package com.example.approject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameController extends PlayerNames{

    @FXML
    private ImageView background;

    @FXML
    private ImageView bluedie;

    @FXML
    private ImageView board1;

    @FXML
    private ImageView dieouter;

    @FXML
    private ImageView greendie;

    @FXML
    private ImageView player1;

    @FXML
    private ImageView player2;

    @FXML
    private Button returnbutton;

    @FXML
    private Stage primaryStage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;

    @FXML
    private Label player1name;

    @FXML
    private Label player2name;

    public void GameController() {
        player1name.setText(getName1());
        player2name.setText(getName2());
    }
    @FXML
    void onReturnClick(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Return to Main Menu");
            alert.setHeaderText("Game progress will be lost");
            alert.setContentText("Are you sure want to return?");
            if(alert.showAndWait().get() == ButtonType.OK){
                primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

