package com.comp2042.ui;

import com.comp2042.logic.board.Score;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

/**
 * Full screen overlay displayed when the game ends
 * Shows the final score, elapsed time, and provides options to restart or return to main menu
 */
public class GameOverPanel {

    private final StackPane overlay;
    private final VBox menuBox;
    private final Button newgameButton;
    private final Button mainmenuButton;
    private Runnable onNewGame;
    private Runnable onMainMenu;
    private final Label scoreLabel;
    private final Label timeLabel;
    private Score score;

    /**
     * Constructs the panel display and placement of the score, time elapsed and New game and main menu buttons in the overlay
     */
    public GameOverPanel() {
        Rectangle background = new Rectangle();
        background.setFill(Color.rgb(0, 0, 0, 0.70));

        Label title = new Label("GAME OVER");
        title.getStyleClass().add("gameOverStyle");
        scoreLabel=new Label();
        scoreLabel.getStyleClass().add("score-display");
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setWrapText(true);

        timeLabel=new Label();
        timeLabel.getStyleClass().add("time-display");
        timeLabel.setTextAlignment(TextAlignment.CENTER);

        newgameButton = new Button("New Game");
        newgameButton.getStyleClass().add("button");

        mainmenuButton = new Button("Main Menu");
        mainmenuButton.getStyleClass().add("button");

        menuBox = new VBox(20, title, scoreLabel, timeLabel, newgameButton, mainmenuButton);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getStyleClass().add("menu-style");

        overlay = new StackPane(background, menuBox);
        overlay.setAlignment(Pos.CENTER);
        overlay.setVisible(false);
        overlay.getStylesheets().add(getClass().getResource("/menu_style.css").toExternalForm());

        setupHandlers();
    }

    /**
     * Handles the actions performed when the New game or Main menu buttons are clicked
     */
    private void setupHandlers() {
        newgameButton.setOnAction(e -> {
            hide();
            if (onNewGame != null) {
                onNewGame.run();
            }
        });

        mainmenuButton.setOnAction(e -> {
            hide();
            if (onMainMenu != null) {
                onMainMenu.run();
            }
        });
    }

    /**
     * Sets the score display in the panel showing the score achieved in the current session and the high score
     * @param score the value of the score to display
     */
    public void setScore(Score score){
        this.score=score;
        scoreLabel.textProperty().bind(score.gameOverTextProperty());
    }

    public void setOnNewGame(Runnable newgame) {
        this.onNewGame = newgame;
    }
    public void setOnMainMenu(Runnable mainmenu) {
        this.onMainMenu = mainmenu;
    }

    /**
     * Sets the elapsed time to display on the game ove screen
     * @param elapsedTime formatted time string to display
     */
    public void setElapsedTime(String elapsedTime){
        timeLabel.setText("Time: "+ elapsedTime);
        timeLabel.setStyle("-fx-background-color: rgba(67,67,67,0.75);");
    }

    /**
     * Shows the game over overlay with the score and time
     */
    public void show() {
        if(score!=null){
            score.setGameOverMessage();
        }
        overlay.setVisible(true);
    }

    /**
     * Hides the game over panel
     */
    public void hide() {
        overlay.setVisible(false);
    }

    /**
     * Bind the size of the overlay to the game board size
     * @param parent the StackPane with game board
     */
    public void bindSizeTo(StackPane parent) {
        Rectangle bg = (Rectangle) overlay.getChildren().get(0);
        bg.widthProperty().bind(parent.widthProperty());
        bg.heightProperty().bind(parent.heightProperty());

        overlay.prefWidthProperty().bind(parent.widthProperty());
        overlay.prefHeightProperty().bind(parent.heightProperty());
    }

    public StackPane getOverlay() {
        return overlay;
    }
}
