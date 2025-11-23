package com.comp2042;

import com.comp2042.logic.bricks.BrickShape;
import javafx.animation.*;
import javafx.application.Application;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GridPane ghostPanel;

    private Rectangle[][] ghostMatrix;

    @FXML
    private GridPane nextBlockPreview;

    @FXML
    private Label levelLabel;

    @FXML
    private GameOverPanel gameOverPanel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    @FXML
    private Label scoreLabel;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private PausePanel pauseOverlay;

    private static MenuScreen menuScreen;
    @FXML
    private Button pauseButton;

    @FXML
    private StackPane stackroot;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                    if(keyEvent.getCode()==KeyCode.ENTER){
                        hardDrop();
                        keyEvent.consume();
                    }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }

                }
            }
        });
        initializePauseOverlay();
        gameOverPanel=new GameOverPanel();
        gameOverPanel.setOnNewGame(this::restartGame);
        gameOverPanel.setOnMainMenu(()->menuScreen.returntoMenu());
        stackroot.getChildren().add(gameOverPanel.getOverlay());
        gameOverPanel.bindSizeTo(stackroot);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.BLACK);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setVisible(false);
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(gamePanel.getLayoutY() + (brick.getyPosition()-2) * brickPanel.getHgap() + (brick.getyPosition()-2) * BRICK_SIZE);

        ghostMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        {
            for (int i = 2; i < ghostMatrix.length; i++) {
                for (int j = 0; j < ghostMatrix[i].length; j++) {
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    rectangle.setFill(Color.TRANSPARENT);
                    ghostMatrix[i][j] = rectangle;
                    gamePanel.add(rectangle, j, i);
                }
            }
        }
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(300),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    public void updateNextShape(NextShapeInfo nextShapeInfo) {
        if (nextBlockPreview == null) {
            return;
        }
        nextBlockPreview.getChildren().clear();

        int[][] nextShape = nextShapeInfo.getShape();
        Color brickColor = nextShapeInfo.getBrick().getColor();

        int offsetX = Math.max(0, (BRICK_SIZE - nextShape[0].length) / 2);
        int offsetY = Math.max(0, (BRICK_SIZE - nextShape.length) / 2);

        for (int i = 0; i < nextShape.length; i++) {
            for (int j = 0; j < nextShape[i].length; j++) {
                int cell = nextShape[i][j];
                if (cell != 0) {
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    setRectangleData(brickColor, rectangle);
                    nextBlockPreview.add(rectangle, j + offsetX, i + offsetY);
                }
            }
        }
    }

    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(gamePanel.getLayoutY() + (brick.getyPosition()-2) * brickPanel.getHgap() + (brick.getyPosition()-2) * BRICK_SIZE);
            Color brickColor = brick.getBrick().getColor();

            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    int cell = brick.getBrickData()[i][j];
                    if (cell != 0) {
                        setRectangleData(brickColor, rectangles[i][j]);
                        rectangles[i][j].setVisible(true);
                    } else {
                        rectangles[i][j].setVisible(false);
                    }
                    setRectangleData(brickColor, rectangles[i][j]);

                }
            }
            refreshGhost(brick, eventListener.getBoardMatrix());
        }
    }


    public void refreshGhost(ViewData brick, int[][] ghostBoard) {
        int ghostY = brick.getyPosition();
        Color brickColor = brick.getBrick().getColor();
        for (int i = 0; i < ghostBoard.length; i++) {
            for (int j = 0; j < ghostBoard[i].length; j++) {
                if (ghostMatrix[i][j] != null) {
                    ghostMatrix[i][j].setVisible(false);
                }
            }
        }
        while (ghostY + 1 < ghostBoard.length && !collides(ghostBoard, brick.getBrickData(), brick.getxPosition(), ghostY + 1)) {
            ghostY++;
        }
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                int cell = brick.getBrickData()[i][j];
                int boardY = ghostY + i;
                int boardX = brick.getxPosition() + j;

                if (boardY >= 0 && boardY < ghostMatrix.length && boardX >= 0 && boardX < ghostMatrix[0].length) {
                    Rectangle rectangle = ghostMatrix[boardY][boardX];
                    if (cell != 0 && ghostBoard[boardY][boardX] == 0) {
                        setRectangleData(brickColor, rectangle);
                        rectangle.setOpacity(0.3);
                        rectangle.setVisible(true);
                    }
                }
            }
        }
    }

    private boolean collides(int[][] board, int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                if (brick[i][j] != 0) {
                    int boardY = y + i;
                    int boardX = x + j;
                    if (boardY >= board.length || boardX < 0 || boardX >= board[0].length) {
                        return true;
                    }
                    if (boardY < 0) {
                        continue;
                    }
                    if (board[boardY][boardX] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void hardDrop(){
        if(isPause.getValue()==Boolean.FALSE && isGameOver.getValue()==Boolean.FALSE){
            ViewData result=eventListener.onHardDropEvent(new MoveEvent(EventType.HARD_DROP,EventSource.USER));
            refreshBrick(result);
            moveDown(new MoveEvent(EventType.DOWN,EventSource.USER));
        }
    }
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int colorInt = board[i][j];
                Color color = BrickShape.getColorInt(colorInt);
                setRectangleData(color, displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(Color color, Rectangle rectangle) {
        rectangle.setFill(color);
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
            updateNextShape(eventListener.getNextShape());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString("%d"));
    }

    public void initializePauseOverlay() {
        pauseOverlay = new PausePanel();

        pauseOverlay.setOnRestart(this::restartGame);
        pauseOverlay.setOnMainMenu(this::returntoMainMenu);
        stackroot.getChildren().add(pauseOverlay.getOverlay());
        pauseOverlay.bindSizeTo(stackroot);
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (timeLine == null) return;

        if (!isPause.get()) {
            timeLine.pause();
            isPause.set(true);
            pauseOverlay.show();
            if (actionEvent != null) {
                ((Button) actionEvent.getSource()).setText("Resume");
            }
        }

        gamePanel.requestFocus();
    }
    public static void setMenuScreen(MenuScreen menuScreen){
        GuiController.menuScreen=menuScreen;
    }
    public void restartGame(){
        if(timeLine!=null){
            timeLine.stop();
        }
        pauseOverlay.hide();
        gameOverPanel.hide();
        isPause.set(false);
        isGameOver.setValue(Boolean.FALSE);
        pauseButton.setText("Pause");
        if(rectangles!=null){
            for(int i=0; i<rectangles.length;i++){
                for (int j=0;j<rectangles[i].length;j++){
                    if (rectangles[i][j] != null) {
                        rectangles[i][j].setVisible(false);
                    }
                }
            }
        }
        if(ghostMatrix!=null){
            for(int i=0; i<ghostMatrix.length;i++){
                for (int j=0;j<ghostMatrix[i].length;j++){
                    if (ghostMatrix[i][j] != null) {
                        ghostMatrix[i][j].setVisible(false);
                    }
                }
            }
        }

        newGame(null);

    }
    public void returntoMainMenu(){
        pauseOverlay.hide();
        isPause.set(false);
        pauseButton.setText("Pause");
        if(timeLine!=null) {
            timeLine.stop();
            menuScreen.returntoMenu();
        }

    }
    public void gameOver() {
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
        updateNextShape(eventListener.getNextShape());
        ghostPanel.getChildren().clear();
    }

}
