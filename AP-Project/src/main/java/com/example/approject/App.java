package com.example.approject;

import javafx.scene.Parent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Loader.fxml")));
            Image icon = new Image(new File("src/main/resources/com.example.approject/snake.png").toURI().toString());
            primaryStage.getIcons().add(icon);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Loader.css")).toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setTitle("Snakes & Ladders");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}