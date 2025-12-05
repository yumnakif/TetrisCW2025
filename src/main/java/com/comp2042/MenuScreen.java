package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuScreen {
    private final Stage stage;
    private Runnable onStartGame;
    private Runnable onExit;
    private Runnable onTimedGame;

    public MenuScreen(Stage stage){
        this.stage=stage;
    }

    public void setOnStartGame(Runnable onStartGame) {
        this.onStartGame = onStartGame;
    }

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    public void setOnTimedGame(Runnable onTimedGame) {
        this.onTimedGame = onTimedGame;
    }
    public void show(){
        Image titleImage= new Image(getClass().getResource("/TETRIS_title.png").toExternalForm());
        ImageView titleView = new ImageView(titleImage);

        titleView.setPreserveRatio(true);
        titleView.setFitWidth(600);

        Button startButton = new Button("Start Game");
        Button exitButton = new Button("Exit");
        Button timedButton = new Button("Timed Game");

        VBox layout = new VBox(20, titleView, startButton,timedButton, exitButton);
        layout.setAlignment(Pos.CENTER);

        Scene menuScene =new Scene(layout,600,680);
        menuScene.getStylesheets().add(getClass().getResource("/menu_style.css").toExternalForm());

        startButton.setOnAction(e -> {
            if (onStartGame != null) onStartGame.run();
        });

        exitButton.setOnAction(e -> {
            if (onExit != null) onExit.run();
        });

        timedButton.setOnAction(e -> {
            if (onTimedGame != null) onTimedGame.run();
        });

        stage.setScene((menuScene));
        stage.setResizable(false);
        stage.show();

    }
    public void returntoMenu(){
        show();
    }
}
