package com.comp2042;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Overlay panel displayed when game is paused
 * Provides restart and return  to main menu options
 */
public class PausePanel {
    private final StackPane pauseOverlay;
    private final VBox pauseMenu;
    private final Button restartButton;
    private final Button mainMenuButton;

    private Runnable onRestart;
    private Runnable isMainMenu;

    /**
     * Creates a pause panel with a translucent overlay and menu buttons
     */
    public PausePanel() {
        Rectangle overlayBackground = new Rectangle();
        overlayBackground.setFill(Color.rgb(0,0,0,0.7));
        pauseMenu = new VBox(20);
        pauseMenu.getStyleClass().add("menu-style");
        pauseMenu.setMaxWidth(300);
        pauseMenu.setMaxHeight(200);

        Label pauseTitle = new Label("PAUSED");
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

    /**
     * Sets up event handlers for restart and main menu buttons
     */
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

    /**
     * Sets the callback for restart button click
     * @param onRestart Runnable to execute when restart is selected
     */
    public void setOnRestart(Runnable onRestart) {
        this.onRestart = onRestart;
    }


    /**
     * Sets the callback for Main menu button click
     * @param isMainMenu Runnable to execute when Main menu is selected
     */
    public void setOnMainMenu(Runnable isMainMenu) {
        this.isMainMenu = isMainMenu;
    }

    /**
     * Binds panel  size to the parent container  for responsive scaling
     * @param parent the parent StackPane to bind dimensions to
     */
    public void bindSizeTo(StackPane parent) {
        Rectangle bg = (Rectangle) pauseOverlay.getChildren().get(0);
        bg.widthProperty().bind(parent.widthProperty());
        bg.heightProperty().bind(parent.heightProperty());
        pauseOverlay.prefWidthProperty().bind(parent.widthProperty());
        pauseOverlay.prefHeightProperty().bind(parent.heightProperty());
    }

    /**
     * shows the pause overlay panel
     */
    public void show() {
        pauseOverlay.setVisible(true);
    }

    /**
     * Hides the pause overlay panel
     */
    public void hide() {
        pauseOverlay.setVisible(false);
    }

    /**
     * Gets the overlay pane for adding to scene
     * @return StackPane containing the pause overlay
     */
    public StackPane getOverlay() {
        return pauseOverlay;
    }

}
