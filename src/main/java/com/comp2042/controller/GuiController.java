package com.comp2042.controller;

import com.comp2042.logic.board.*;
import com.comp2042.input.EventSource;
import com.comp2042.input.EventType;
import com.comp2042.input.InputEventListener;
import com.comp2042.logic.modes.TimedMode;
import com.comp2042.ui.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main JavaFX controller for Tetris game GUI.
 * Manages game visualization, user input, timing, and game state transitions.
 */
public class GuiController implements Initializable {

    @FXML
    private GridPane gamePanel;
    @FXML
    private GridPane brickPanel;
    @FXML
    private GridPane ghostPanel;
    @FXML
    private GridPane nextBlockPreview;
    @FXML
    private Group groupNotification;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label stopwatchLabel;
    @FXML
    private VBox stopwatchBox;
    @FXML
    private Label timerLabel;
    @FXML
    private VBox timerBox;
    @FXML
    private Label linesRemovedLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Button pauseButton;
    @FXML
    private StackPane stackroot;
    @FXML
    private Label highScoreLabel;

    private Timeline timeLine;
    private Timeline downTimeline;
    private boolean downKeyPressed;
    private InputEventListener eventListener;
    private Stopwatch stopwatch;
    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    private static MenuScreen menuScreen;
    private boolean isTimedMode=false;
    private TimedMode timedMode;
    private GameRender renderer;
    private InputHandler inputHandler;
    private GameState state;
    Rectangle fogOverlay;

    /**
     * Initializes the GUI components and sets up game systems
     * @param location The location used to resolve relative paths
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        renderer=new GameRender(gamePanel,brickPanel,ghostPanel);
        renderer.setNextBlockPreview(nextBlockPreview);
        state=new GameState(stackroot);

        stopwatch=new Stopwatch();
        stopwatch.bindtoLabel(stopwatchLabel);

        state.pauseRestart(this::restartGame);
        state.pauseMainMenu(this::returntoMainMenu);
        state.gameOverRestart(this::restartGame);
        state.gameOverMainMenu(()->menuScreen.returntoMenu());

        fogOverlay=new Rectangle();
        fogOverlay.setVisible(false);
        fogOverlay.setMouseTransparent(true);
        fogOverlay.setFill(Color.rgb(58,58,58,0.83));

        Pane gamePanelParent = (Pane) gamePanel.getParent();
        fogOverlay.widthProperty().bind(gamePanelParent.widthProperty());
        fogOverlay.heightProperty().bind(gamePanelParent.heightProperty());
        stackroot.getChildren().add(1,fogOverlay);

        highScoreLabel.textProperty().bind(state.getScore().highScoreProperty().asString("%d"));
        timerBox.setVisible(false);
        timerBox.setManaged(false);


        setupTimeline();
    }

    /**
     * Initializes input handlers for keyboard controls
     */
    public void initInputHandler(){
        inputHandler=new InputHandler(eventListener,this::hardDrop, this::handleNewGame);
        gamePanel.setOnKeyPressed(e->{
                if(!isPause.get()&& !isGameOver.get()){
                    if(e.getCode()==KeyCode.DOWN || e.getCode()==KeyCode.S){
                        downKeyPressed=true;
                        downTimeline.play();
                    }
                    else{
                        inputHandler.handle(e,isPause.get(),isGameOver.get());
                    }
                }
        });
        gamePanel.setOnKeyReleased(e->{
            if(e.getCode()==KeyCode.DOWN || e.getCode()==KeyCode.S){
                downKeyPressed=false;
                downTimeline.stop();
            }
        });
    }

    /**
     * Set up one timeline for automatic brick movement and one for the movement from user key input
     * Using a timeline for user key movement down for smoother dropping of the brick
     */
    private void setupTimeline(){
        timeLine = new Timeline(new KeyFrame(Duration.millis(300),
                ae -> {
                    if(!downKeyPressed){
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD));
                    }
                }
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);

        downTimeline=new Timeline(new KeyFrame(Duration.millis(50),
                ae-> {
                    if(downKeyPressed) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                    }else{
                        downTimeline.stop();
                    }
        }
        ));
        downTimeline.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    /**
     * Updates game speed based on level progression
     * @param newSpeed New millisecond delay between automatic moves
     */
    public void updateTimelineSpeed(int newSpeed){
        if(timeLine!=null){
            timeLine.stop();
        }
        timeLine=new Timeline(new KeyFrame(Duration.millis(newSpeed), se->{
            moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD));
        }));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    /**
     * Gets the game renderer instance
     * @return the GameRender object managing visual display
     */
    public GameRender getRenderer(){
        return renderer;
    }

    /**
     * Renders the current brick and its ghost preview
     * @param brick ViewData containing brick position and shape
     */
    public void brickRender(ViewData brick){
        if(brick!=null && renderer!=null && eventListener!=null){
            int[][] board =eventListener.getBoardMatrix();
            renderer.refreshBrick(brick);
            renderer.refreshGhost(brick,board);
        }
    }

    /**
     * Instantly drops the brick to the bottom of the board to where it places
     * Awards score bonus if lines are cleared
     */
    public void hardDrop(){
        if(isPause.getValue()==Boolean.FALSE && isGameOver.getValue()==Boolean.FALSE){
            DownData result=eventListener.onHardDropEvent(new MoveEvent(EventType.HARD_DROP,EventSource.USER));
            setScoreNotification(result);
            brickRender(result.getViewData());
        }
    }

    /**
     * Moves the current brick down one cell manually or automatic
     * Awards score bonus if lines are cleared
     * @param event MoveEvent describing the move action
     */
    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE && eventListener!=null) {
            DownData downData = eventListener.onDownEvent(event);
            setScoreNotification(downData);
            if(downData!=null && downData.getViewData()!=null) {
                brickRender(downData.getViewData());
                renderer.updateNextShape(eventListener.getNextShape());
            }
        }
        gamePanel.requestFocus();
    }

    /**
     * Displays the notification for the score bonus received from clearing lines
     * after moving the brick down or hard drop actions
     * @param downData DownData object containing information about the line clearing operation, the number of lines cleared
     *                 and the score bonus achieved
     */
    private void setScoreNotification(DownData downData) {
        if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
            if(downData.getClearRow().getLinesRemoved()>1){
                NotificationPanel notificationPanel = new NotificationPanel("COMBO! +" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            else{
            NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
            groupNotification.getChildren().add(notificationPanel);
            notificationPanel.showScore(groupNotification.getChildren());
            }
        }
    }


    /**
     * Sets the input event listener for game logic.
     * @param eventListener InputEventListener to handle game events
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Enables or disables the timed game mode
     * If timed mode is enabled the countdown timer is started
     * Game over panel shown when timer ends
     * @param istimedMode {@code true} enables the timed mode
     *                    {@code false} disables the timed mode and timer UI is hidden
     */
    public void setTimedMode(boolean istimedMode) {
        isTimedMode = istimedMode;
        if(istimedMode){
            this.timedMode=new TimedMode();
            timerBox.setVisible(true);
            timerBox.setManaged(true);
            stopwatchBox.setVisible(false);
            stopwatchBox.setManaged(false);

            timedMode.bindTimer(timerLabel);

            timedMode.setOnGameEnd(()->{

                Platform.runLater(this::gameOver);
            });
        } else{
            timerBox.setVisible(false);
            timerBox.setManaged(false);
        }
    }

    /**
     * Binds score display to game score property.
     * @param integerProperty Property containing current score
     */
    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString("%d"));
        state.getScore().add(integerProperty.get());
        integerProperty.addListener((obs, oldVal, newVal) -> {
            int difference=newVal.intValue()-state.getScore().scoreProperty().get();
            state.getScore().add(difference);
        });
    }

    /**
     * Binds lines removed display to game property
     * @param linesProperty Property containing lines cleared count
     */
    public void bindLinesRemoved(IntegerProperty linesProperty){
        linesRemovedLabel.textProperty().bind(linesProperty.asString("%d"));
    }

    /**
     * Binds level display to game level property
     * @param levelProperty Property containing current level
     */
    public void bindLevel(IntegerProperty levelProperty){
        levelLabel.textProperty().bind(levelProperty.asString("%d"));
    }

    /**
     * Pauses or resumes the game
     * @param actionEvent button click event from pause button
     */
    public void pauseGame(ActionEvent actionEvent) {
        state.pauseToggle(pauseButton);
        if (state.isPaused()) {
            timeLine.pause();
            downTimeline.stop();
            downKeyPressed = false;
            isPause.set(true);
            if(isTimedMode && timedMode!=null){
                timedMode.pause();
            }
            else{
                stopwatch.stop();
            }
        }
        else{
            timeLine.play();
            isPause.set(false);
            if(isTimedMode && timedMode!=null){
                timedMode.resume();
            }
            else{
                stopwatch.start();
            }
        }
        gamePanel.requestFocus();
    }

    /**
     * Sets the reference to the main menu screen
     * @param menuScreen menu screen instance
     */
    public static void setMenuScreen(MenuScreen menuScreen){
        GuiController.menuScreen=menuScreen;
    }

    /**
     * Restarts game with fresh state
     */
    public void restartGame(){
        if(timeLine!=null){
            timeLine.stop();
            downTimeline.stop();
        }
        state.hidePause();
        state.hideGameOver();
        isPause.set(false);
        isGameOver.setValue(Boolean.FALSE);
        pauseButton.setText("Pause");
        fogOverlay.setVisible(false);
        state.resetScore();
        renderer.clearBoard();
        if(isTimedMode && timedMode!=null){
            timedMode.reset();
        }

        newGame(null);

    }

    /**
     * Returns to the main menu screen
     */
    public void returntoMainMenu(){
        state.hidePause();
        isPause.set(false);
        pauseButton.setText("Pause");
        if(timeLine!=null) {
            timeLine.stop();
            menuScreen.returntoMenu();
        }

    }

    /**
     * Handles game over condition and displays game over screen
     * Stops the timeline and stopwatch calls the GameState to show game over panel
     */
    public void gameOver() {
        isGameOver.setValue(Boolean.TRUE);
        timeLine.stop();
        if(isTimedMode && timedMode!=null){
            timedMode.stop();
            String elapsedTime= timerLabel.getText();
            state.setElapsedTime(elapsedTime);
        }else {
            stopwatch.stop();
            String elapsedTime=stopwatchLabel.getText();
            state.setElapsedTime(elapsedTime);
        }

        state.showGameOver();
    }

    /**
     * Applies or removes fog overlay effect
     * @param hasFog true to show fog, false to hide it
     */
    public void applyfog(boolean hasFog){
        fogOverlay.setVisible(hasFog);
    }
    private void handleNewGame(){
        newGame(null);
    }

    /**
     * Starts a new game session
     * Reset the isPause and isGameOver values
     * Clears ghost children and restarts stopwatch
     * @param actionEvent button click event from new game button
     */
    public void newGame(ActionEvent actionEvent) {
        if(timeLine!=null){
            timeLine.stop();
        }
        eventListener.createNewGame();
        gamePanel.requestFocus();

        if(isTimedMode){
            stopwatch.reset();
            if(timedMode!=null){
                timedMode.start();
            }
            timeLine.play();
            isPause.setValue(Boolean.FALSE);
            isGameOver.setValue(Boolean.FALSE);
            renderer.updateNextShape(eventListener.getNextShape());
            ghostPanel.getChildren().clear();

        }else {
            stopwatch.restart();
            timeLine.play();
            isPause.setValue(Boolean.FALSE);
            isGameOver.setValue(Boolean.FALSE);
            renderer.updateNextShape(eventListener.getNextShape());
            ghostPanel.getChildren().clear();
        }
    }
}