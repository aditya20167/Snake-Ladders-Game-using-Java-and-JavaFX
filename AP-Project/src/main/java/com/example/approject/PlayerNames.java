package com.example.approject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PlayerNames {
    @FXML
    private Button playbutton;
    @FXML
    private Stage primaryStage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private TextField player1;
    @FXML
    private TextField player2;

    String name1, name2;
    @FXML
    void onPlayButtonClick(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
            primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void player1input(ActionEvent event) {
        try {
            name1 = player1.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void player2input(ActionEvent event) {
        try {
            name2 = player2.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

