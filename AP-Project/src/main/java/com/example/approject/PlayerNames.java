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
    public TextField player1;
    @FXML
    public TextField player2;

    private String name1, name2;

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
    void onReturnClick(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
            primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void player1input(ActionEvent event) {
        try {
            name1 = player1.getText();
            if (name1 == null) {
                name1 = "Player 1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void player2input(ActionEvent event) {
        try {
            name2 = player2.getText();
            if (name2 == null) {
                name2 = "Player 2";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getName1() {
        return name1;
    }
    public String getName2() {
        return name2;
    }
}

