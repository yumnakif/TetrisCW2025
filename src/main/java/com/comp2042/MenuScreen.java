package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Main menu screen for the tetris game with start and exit options
 * Displays game title image and provides navigation to game or exit
 */
public class MenuScreen {
    private final Stage stage;
    private Runnable onStartGame;
    private Runnable onExit;

    /**
     * Creates a menu screen for the specified stage
     * @param stage the javafx stage to display the menu on
     */
    public MenuScreen(Stage stage){
        this.stage=stage;
    }

    /**
     * Sets the callback for when the game button is clicked
     * @param onStartGame Runnable to execute when start game is selected
     */
    public void setOnStartGame(Runnable onStartGame) {
        this.onStartGame = onStartGame;
    }

    /**
     * Sets the callback for when Exit button is clicked
     * @param onExit Runnable to execute when exit is selected
     */
    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    /**
     * Displays the menu screen with the  title image and buttons
     * Loads CSS styling and sets up button actions
     */
    public void show(){
        Image titleImage= new Image(getClass().getResource("/TETRIS_title.png").toExternalForm());
        ImageView titleView = new ImageView(titleImage);
        titleView.setPreserveRatio(true);
        titleView.setFitWidth(600);

        Button startButton = new Button("Start Game");
        Button exitButton = new Button("Exit");
        VBox layout = new VBox(20, titleView, startButton, exitButton);
        layout.setAlignment(Pos.CENTER);

        Scene menuScene =new Scene(layout,600,680);
        menuScene.getStylesheets().add(getClass().getResource("/menu_style.css").toExternalForm());

        startButton.setOnAction(e -> {
            if (onStartGame != null) onStartGame.run();
        });

        exitButton.setOnAction(e -> {
            if (onExit != null) onExit.run();
        });
        stage.setScene((menuScene));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Returns to the main menu screen
     */
    public void returntoMenu(){
        show();
    }
}
