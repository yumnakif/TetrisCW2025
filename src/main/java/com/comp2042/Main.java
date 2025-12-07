package com.comp2042;

import com.comp2042.controller.GameController;
import com.comp2042.controller.GuiController;
import com.comp2042.ui.MenuScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Main entry point for the tetris game application
 * Manages the JavaFX application and coordinates between menu screen and game interface
 *
 * @see MenuScreen
 * @see GuiController
 * @see GameController
 */
public class Main extends Application {
    private MenuScreen menuScreen;


    /**
     * The entry point for the application
     * Initialized and displays the menu screen and sets up event handlers for gem start and exit actions
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if any initialization error occurs
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        menuScreen=new MenuScreen(primaryStage);
        menuScreen.setOnStartGame(()-> loadGameScene(primaryStage));
        menuScreen.setOnTimedGame(()-> startTimedGame(primaryStage));
        menuScreen.setOnExit(primaryStage::close);
        menuScreen.show();
        Image logo=new Image("Logo2.png");
        primaryStage.getIcons().add(logo);
    }


    /**
     * Loads and displays the main game scene
     * Loads the FXML layout and initializes the controllers
     * Sets up game interface
     * @param primaryStage the stage to display the game scene on
     */
    public void loadGameScene(Stage primaryStage){
        try{
            URL location = Main.class.getClassLoader().getResource("gameLayout.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent root = fxmlLoader.load();
            GuiController c = fxmlLoader.getController();
            GuiController.setMenuScreen(menuScreen);

            Scene scene = new Scene(root, 600, 680);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("TetrisJFX");
            primaryStage.show();
            new GameController(c);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * Loads the timed mode game when the Timed Mode button is clicked from main menu
     * @param primaryStage the stage to display the game scene on
     */
    public void startTimedGame(Stage primaryStage){
        try{
            URL location = Main.class.getClassLoader().getResource("gameLayout.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);

            Parent root =fxmlLoader.load();
            GuiController c = fxmlLoader.getController();
            GuiController.setMenuScreen(menuScreen);

            c.setTimedMode(true);
            new GameController(c);
            Scene scene = new Scene(root, 600, 680);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TetrisJFX");
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Main method to launch the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
