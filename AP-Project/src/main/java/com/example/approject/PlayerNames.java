package com.example.approject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

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
    private TextField player1Name;
    @FXML
    private TextField player2Name;

    private String name1, name2;
    @FXML
    void onPlayButtonClick(ActionEvent event) {
        try {
            String player1 = player1Name.getText();
            String player2 = player2Name.getText();

            Random rand = new Random();
            int firstRoll = rand.nextInt(3) + 1;
            InputStream firstStream = new FileInputStream("C:/Users/adity/Documents/GitHub/AP_Project/AP-Project/src/main/resources/com/example/approject/board_" + firstRoll + ".png");
            Image board = new Image(firstStream);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
            root = loader.load();

            GameController gamer = loader.getController();
            gamer.setPlayerNames(player1, player2);
            gamer.setStart();
            gamer.setBoard(board);

            primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }
}

