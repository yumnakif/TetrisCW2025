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

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        MenuScreen menu= new MenuScreen(primaryStage);
        menu.show();
        Image logo=new Image("Logo2.png");
        primaryStage.getIcons().add(logo);
    }

    public static void loadGameScene(Stage primaryStage) throws Exception{
        URL location = Main.class.getClassLoader().getResource("gameLayout.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 448, 657);
        primaryStage.setScene(scene);
        primaryStage.show();
        new GameController(c);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
