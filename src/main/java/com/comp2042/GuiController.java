package com.comp2042;

import com.comp2042.logic.bricks.BrickShape;
import com.comp2042.logic.levels.Level;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

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
    public void initInputHandler(){
        inputHandler=new InputHandler(eventListener,this::hardDrop, this::handleNewGame);
        gamePanel.setOnKeyPressed(e->inputHandler.handle(e,isPause.get(),isGameOver.get()));
    }

    private void setupTimeline(){
        timeLine = new Timeline(new KeyFrame(Duration.millis(300),
                ae -> {
                    moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD));
                }
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

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
    public GameRender getRenderer(){
        return renderer;
    }
    public void brickRender(ViewData brick){
        if(brick!=null && renderer!=null && eventListener!=null){
            int[][] board =eventListener.getBoardMatrix();
            renderer.refreshBrick(brick);
            renderer.refreshGhost(brick,board);
        }
    }
    public void hardDrop(){
        if(isPause.getValue()==Boolean.FALSE && isGameOver.getValue()==Boolean.FALSE){
            DownData result=eventListener.onHardDropEvent(new MoveEvent(EventType.HARD_DROP,EventSource.USER));
            if (result.getClearRow() != null && result.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + result.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            brickRender(result.getViewData());

        }
    }
    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE && eventListener!=null) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            if(downData!=null && downData.getViewData()!=null) {
                brickRender(downData.getViewData());
                renderer.updateNextShape(eventListener.getNextShape());
            }
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

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
                Platform.runLater(()->{
                    gameOver();
                });
            });
        } else{
            timerBox.setVisible(false);
            timerBox.setManaged(false);
        }
    }

    public static void setMenuScreen(MenuScreen menuScreen){
        GuiController.menuScreen=menuScreen;
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString("%d"));
        integerProperty.addListener((obs, oldVal, newVal) -> {
            state.getScore().add(newVal.intValue() - oldVal.intValue());
        });
    }
    public void bindLinesRemoved(IntegerProperty linesProperty){
        linesRemovedLabel.textProperty().bind(linesProperty.asString("%d"));
    }
    public void bindLevel(IntegerProperty levelProperty){
        levelLabel.textProperty().bind(levelProperty.asString("%d"));
    }
    public void pauseGame(ActionEvent actionEvent) {
        state.pauseToggle(pauseButton);
        if (state.isPaused()) {
            timeLine.pause();
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


    public void restartGame(){
        if(timeLine!=null){
            timeLine.stop();
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
    public void returntoMainMenu(){
        state.hidePause();
        isPause.set(false);
        pauseButton.setText("Pause");
        if(timeLine!=null) {
            timeLine.stop();
            menuScreen.returntoMenu();
        }

    }
    public void gameOver() {
        isGameOver.setValue(Boolean.TRUE);
        timeLine.stop();
        if(isTimedMode && timedMode!=null){
            timedMode.stop();
        }else {
            stopwatch.stop();
        }
        state.showGameOver();
    }

    public void applyfog(boolean hasFog){
        fogOverlay.setVisible(hasFog);
    }
    private void handleNewGame(){
        newGame(null);
    }

    public void newGame(ActionEvent actionEvent) {
        if(timeLine!=null){timeLine.stop();}
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