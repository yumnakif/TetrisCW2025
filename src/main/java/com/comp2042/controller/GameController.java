package com.comp2042.controller;

import com.comp2042.logic.board.*;
import com.comp2042.input.EventSource;
import com.comp2042.input.InputEventListener;
import com.comp2042.logic.levels.Level;
import com.comp2042.logic.levels.LevelController;
import com.comp2042.ui.ViewData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

/**
 * Main game controller handling the game logic, input events and level progression
 * Implements InputEventListener to process user inputs and manage game state
 */
public class GameController implements InputEventListener {
    private final int height= 34;
    private final int width=20;

    private int totalLines=0;
    private int fallSpeed = 300;
    private IntegerProperty linesProperty=new SimpleIntegerProperty(0);
    private Board board = new SimpleBoard(height, width);
    private final GuiController viewGuiController;
    private LevelController levelController =new LevelController();

    /**
     * Create a new game controller with specified GUI controller
     * @param c the GUI controller for rendering and user inputs
     */
    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initInputHandler();
        viewGuiController.getRenderer().initGameView(board.getBoardMatrix());
        viewGuiController.getRenderer().initBrick(board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindLinesRemoved(linesProperty);
        viewGuiController.bindLevel(levelController.getLevelProperty());
        viewGuiController.getRenderer().updateNextShape(board.getNextShape());
        viewGuiController.newGame(null);
    }

    /**
     * Handles down movement events, automatic or user initiated
     * @param event MoveEvent contatining event type and source
     * @return DownData with clearance results and current view data
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {

        if(event.getEventSource()== EventSource.THREAD && levelController.getCurrentLevel().hasWind()){
            boolean wind= levelController.getCurrentLevel().applyWind(board);
            if(wind){
                ViewData viewData=board.getViewData();
                viewGuiController.brickRender(viewData);
            }
        }

        boolean canMove = board.moveBrickDown();
        ClearRow clearRow ;

        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow=board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                totalLines= totalLines + clearRow.getLinesRemoved();
                linesRemovedProperty().set(totalLines);
                board.getScore().add(clearRow.getScoreBonus());
                checkLevelup();
            }

            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }
            viewGuiController.getRenderer().updateNextShape(board.getNextShape());
            viewGuiController.getRenderer().refreshBackground(board.getBoardMatrix());

        } else {
            clearRow=new ClearRow(0,board.getBoardMatrix(),0);
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Handles left movement event with revrse control
     * @param event MoveEvent containing event type and source
     * @return ViewData with updated brick position
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        if(levelController.getCurrentLevel().hasReverseControl()&& levelController.getCurrentLevel().isSwapped()){
            board.moveBrickRight();
        }
        else {
            board.moveBrickLeft();
        }
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return viewData;
    }

    /**
     * Handles right movement events with reverse control
     * @param event MoveEvent containing event type and source
     * @return ViewData with updated brick position
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        if(levelController.getCurrentLevel().hasReverseControl()&& levelController.getCurrentLevel().isSwapped()){
            board.moveBrickLeft();
        }else {
            board.moveBrickRight();
        }
            ViewData viewData = board.getViewData();
            viewGuiController.brickRender(viewData);
            return viewData;

    }

    /**
     * Handles brick rotation events
     * @param event MoveEvent containing event type and source
     * @return ViewData with updated brick position
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateBrick();
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return viewData;
    }

    /**
     * Handles hard drop events to instantly place brick
     * @param event MoveEvent containing event type and source
     * @return ViewData with clearance results and view data
     */
    @Override
    public DownData onHardDropEvent(MoveEvent event){
        boolean canMove=true;
        while(canMove){
            canMove=board.moveBrickDown();
            if(canMove && event.getEventSource()==EventSource.USER){
                board.getScore().add(1);
            }
        }
        board.mergeBrickToBackground();
        ClearRow clearRow=board.clearRows();
        if (clearRow.getLinesRemoved()>0){
            totalLines= totalLines + clearRow.getLinesRemoved();
            linesRemovedProperty().set(totalLines);
            board.getScore().add(clearRow.getScoreBonus());
            checkLevelup();
        }
        viewGuiController.getRenderer().refreshBackground(board.getBoardMatrix());
        if (board.createNewBrick()) {
            viewGuiController.gameOver();
        }
        viewGuiController.getRenderer().updateNextShape(board.getNextShape());
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return new DownData(clearRow,viewData);
    }

    /**
     * Gets information about the next brick shape
     * @return NextShapeInfo containing next brick data
     */
    @Override
    public NextShapeInfo getNextShape(){
        return board.getNextShape();
}

    /**
     * Gets the current game board matrix
     * @return 2D array showing the game board state
     */
    @Override
    public int[][] getBoardMatrix(){
        return board.getBoardMatrix();
    }

    /**
     * Updated the brick speed based on the current level multiplier
     * If new speed is below 50 the default is set to 50 to prevent unplayable speed
     */
    private void updateSpeed(){
        int newSpeed=(int) (fallSpeed/ levelController.getCurrentLevel().getSpeedMultiplier());
        if(newSpeed<50){
            newSpeed=50;
        }
        viewGuiController.updateTimelineSpeed(newSpeed);
    }

    /**
     *Gets observable property for lines removed count
     * @return IntegerProperty tracking cleared lines
     */
    public IntegerProperty linesRemovedProperty(){
        return linesProperty;
    }

    /**
     * Increments the level based on players total lines cleared
     * Level increases every 5 lines cleared
     */
    private void checkLevelup() {
        int expLevel=totalLines/3 +1;
        if(expLevel> levelController.getCurrentLevel().getLevelNumber()){
            while(expLevel> levelController.getCurrentLevel().getLevelNumber()){
                Label levelLabel=new Label("Level: "+ levelController.getCurrentLevel().getLevelNumber());
                levelLabel.setStyle("-fx-font-size:20; -fx-text-fill: gold;");

                levelController.nextLevel();
            }
            updateSpeed();
            applyLevelFunction();
        }
    }

    /**
     * Applies level specific effects when transitioning between lvels
     * Handles the static and dynamic obstacles, fog, controls swap, etc
     */
    private void applyLevelFunction(){
        Level level= levelController.getCurrentLevel();
        Level previousLevel= levelController.getPreviousLevel();

        if(previousLevel!=null && previousLevel.hasStaticObstacles() && !level.hasStaticObstacles()){
            removeObstacles(board.getBoardMatrix());
        }
        if(level.hasStaticObstacles()){
            level.placeStaticObject(board);
        }

        if(level.hasDynamicObstacles()){
            level.startDynamicObstacles(board);
        } else{
            level.stopObstacles();
        }

        if(level.hasReverseControl()){
            level.swapKeys();
        }

        viewGuiController.applyfog(level.hasFog());
        viewGuiController.getRenderer().refreshBackground(board.getBoardMatrix());
    }

    private void removeObstacles(int[][] boardMatrix) {
        for(int i=0;i< boardMatrix.length;i++){
            for(int j=0;j<boardMatrix[i].length;j++) {
                if (boardMatrix[i][j] == 8) {
                    boardMatrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * Resets the game to initial state for a new game session
     */
    @Override
    public void createNewGame() {
        totalLines=0;
        linesProperty.set(0);
        levelController.reset();
        board.newGame();
        viewGuiController.getRenderer().clearBoard();
        viewGuiController.getRenderer().initGameView(board.getBoardMatrix());
        viewGuiController.getRenderer().initBrick(board.getViewData());
        viewGuiController.getRenderer().updateNextShape(board.getNextShape());

        updateSpeed();
    }

}
