package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    private MenuScreen menuScreen;

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        menuScreen=new MenuScreen(primaryStage);
        menuScreen.setOnStartGame(()-> loadGameScene(primaryStage));
        menuScreen.setOnExit(primaryStage::close);
        menuScreen.show();
        Image logo=new Image("Logo2.png");
        primaryStage.getIcons().add(logo);
    }

    public void loadGameScene(Stage primaryStage){
        try{
            URL location = Main.class.getClassLoader().getResource("gameLayout.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent root = fxmlLoader.load();
            GuiController c = fxmlLoader.getController();
            GuiController.setMenuScreen(menuScreen);

            Scene scene = new Scene(root, 600, 657);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("TetrisJFX");
            primaryStage.show();
            new GameController(c);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
