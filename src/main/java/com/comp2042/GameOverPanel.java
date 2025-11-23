package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameOverPanel {

    private final StackPane overlay;
    private final VBox menuBox;
    private final Button newgameButton;
    private final Button mainmenuButton;
    private Runnable onNewGame;
    private Runnable onMainMenu;

    public GameOverPanel() {

        Rectangle background = new Rectangle();
        background.setFill(Color.rgb(0, 0, 0, 0.70));

        Label title = new Label("GAME OVER");
        title.getStyleClass().add("gameOverStyle");

        newgameButton = new Button("New Game");
        newgameButton.getStyleClass().add("button");
        mainmenuButton = new Button("Main Menu");
        mainmenuButton.getStyleClass().add("button");
        menuBox = new VBox(20, title, newgameButton, mainmenuButton);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getStyleClass().add("pause-menu");

        overlay = new StackPane(background, menuBox);
        overlay.setAlignment(Pos.CENTER);
        overlay.setVisible(false);
        overlay.getStylesheets().add(getClass().getResource("/menu_style.css").toExternalForm());

        setupHandlers();
    }

    private void setupHandlers() {
        newgameButton.setOnAction(e -> {
            hide();
            if (onNewGame != null) onNewGame.run();
        });

        mainmenuButton.setOnAction(e -> {
            hide();
            if (onMainMenu != null) onMainMenu.run();
        });
    }

    public void setOnNewGame(Runnable newgame) {
        this.onNewGame = newgame;
    }
    public void setOnMainMenu(Runnable mainmenu) {
        this.onMainMenu = mainmenu;
    }
    public void show() {
        overlay.setVisible(true);
    }
    public void hide() {
        overlay.setVisible(false);
    }
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
