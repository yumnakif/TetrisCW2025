package com.comp2042;

import com.comp2042.logic.levels.Level;
import com.comp2042.logic.levels.Level4;
import com.comp2042.logic.levels.LevelManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameController implements InputEventListener {
    private final int height= 34;
    private final int width=20;

    private int totalLines=0;
    private int fallSpeed = 300;
    private IntegerProperty linesProperty=new SimpleIntegerProperty(0);
    private Board board = new SimpleBoard(height, width);
    private final GuiController viewGuiController;
    private LevelManager levelManager=new LevelManager();

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initInputHandler();
        viewGuiController.getRenderer().initGameView(board.getBoardMatrix());
        viewGuiController.getRenderer().initBrick(board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindLinesRemoved(linesProperty);
        viewGuiController.bindLevel(levelManager.getLevelProperty());
        viewGuiController.getRenderer().updateNextShape(board.getNextShape());
        viewGuiController.newGame(null);
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {

        if(event.getEventSource()==EventSource.THREAD && levelManager.getCurrentLevel().hasWind()){
            boolean wind=levelManager.getCurrentLevel().applyWind(board);
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


    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        if(levelManager.getCurrentLevel().hasReverseControl()&&levelManager.getCurrentLevel().isSwapped()){
            board.moveBrickRight();
        }
        else {
            board.moveBrickLeft();
        }
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return viewData;
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        if(levelManager.getCurrentLevel().hasReverseControl()&&levelManager.getCurrentLevel().isSwapped()){
            board.moveBrickLeft();
        }else {
            board.moveBrickRight();
        }
            ViewData viewData = board.getViewData();
            viewGuiController.brickRender(viewData);
            return viewData;

    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateBrick();
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return viewData;
    }

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

    @Override
    public NextShapeInfo getNextShape(){
        return board.getNextShape();
}

    @Override
    public int[][] getBoardMatrix(){
        return board.getBoardMatrix();
    }


    private void updateSpeed(){
        //update the new calculated speed according to level to GuiController to update the timeline
        int newSpeed=(int) (fallSpeed/levelManager.getCurrentLevel().getSpeedMultiplier());
        if(newSpeed<50){
            newSpeed=50;
        }
        viewGuiController.updateTimelineSpeed(newSpeed);
    }

    public IntegerProperty linesRemovedProperty(){
        return linesProperty;
    }

    private void checkLevelup() {
        //increase level every 5 lines cleared
        int expLevel=totalLines/5 +1;
        if(expLevel>levelManager.getCurrentLevel().getLevelNumber()){
            while(expLevel>levelManager.getCurrentLevel().getLevelNumber()){
                levelManager.nextLevel();
            }
            updateSpeed();
            applyLevelFunction();
        }
    }

    private void applyLevelFunction(){
        Level level=levelManager.getCurrentLevel();
        Level previousLevel=levelManager.getPreviousLevel();

        if(previousLevel!=null && previousLevel.hasStaticObstacles() && !level.hasStaticObstacles()){
            int[][] matrix=board.getBoardMatrix();
            for(int i=0;i< matrix.length;i++){
                for(int j=0;j<matrix[i].length;j++) {
                    if (matrix[i][j] == 8) {
                        matrix[i][j] = 0;
                    }
                }
            }
        }
        if(level.hasStaticObstacles()){
            level.placeStaticObject(board);
        }
        if(level.hasDynamicObstacles()){
            level.startDynamicObstacles(board);
        }
        if(level.hasReverseControl()){
            level.startDynamicObstacles(board);
        }
        viewGuiController.applyfog(level.hasFog());

        viewGuiController.getRenderer().refreshBackground(board.getBoardMatrix());
    }

    @Override
    public void createNewGame() {
        totalLines=0;
        linesProperty.set(0);
        levelManager.reset();
        board.newGame();
        viewGuiController.getRenderer().clearBoard();
        viewGuiController.getRenderer().initGameView(board.getBoardMatrix());
        viewGuiController.getRenderer().initBrick(board.getViewData());
        viewGuiController.getRenderer().updateNextShape(board.getNextShape());

        updateSpeed();
    }

}
