package com.comp2042;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class PausePanel {
    private final StackPane pauseOverlay;
    private final VBox pauseMenu;
    private final Button restartButton;
    private final Button mainMenuButton;

    //private Timeline gametimeline;
    private Runnable onRestart;
    private Runnable isMainMenu;

    public PausePanel() {
        Rectangle overlayBackground = new Rectangle();
        overlayBackground.setFill(Color.rgb(0,0,0,0.7));
        pauseMenu = new VBox(20);
        pauseMenu.getStyleClass().add("pause-menu");
        pauseMenu.setMaxWidth(300);
        pauseMenu.setMaxHeight(200);

        Label pauseTitle = new Label("GAME PAUSED");
        pauseTitle.getStyleClass().add("pause-title");

        restartButton = new Button("Restart");
        restartButton.getStyleClass().add("button");

        mainMenuButton = new Button("Main menu");
        mainMenuButton.getStyleClass().add("button");

        pauseMenu.getChildren().addAll(pauseTitle, restartButton, mainMenuButton);

        pauseOverlay = new StackPane(overlayBackground,pauseMenu);
        pauseOverlay.setAlignment(Pos.CENTER);
        pauseOverlay.setVisible(false);
        pauseOverlay.getStylesheets().add(getClass().getResource("/menu_style.css").toExternalForm());

        setupEventHandlers();
    }

    private void setupEventHandlers(){
        restartButton.setOnAction(e->{
            if(onRestart!=null){
                onRestart.run();
            }
        });

        mainMenuButton.setOnAction(e->{
            if(isMainMenu!=null){
                isMainMenu.run();
            }
        });
    }
    public void setOnRestart(Runnable onRestart) {
        this.onRestart = onRestart;
    }

    public void setOnMainMenu(Runnable isMainMenu) {
        this.isMainMenu = isMainMenu;
    }

    public void bindSizeTo(StackPane parent) {
        Rectangle bg = (Rectangle) pauseOverlay.getChildren().get(0);
        bg.widthProperty().bind(parent.widthProperty());
        bg.heightProperty().bind(parent.heightProperty());
        pauseOverlay.prefWidthProperty().bind(parent.widthProperty());
        pauseOverlay.prefHeightProperty().bind(parent.heightProperty());
    }

    public void show() {
        pauseOverlay.setVisible(true);
    }

    public void hide() {
        pauseOverlay.setVisible(false);
    }

    public boolean isVisible() {
        return pauseOverlay.isVisible();
    }

    // Get the root node for adding to scene graph
    public StackPane getOverlay() {
        return pauseOverlay;
    }

}
