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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController {

    @FXML
    private ImageView background;
    @FXML
    private Button exitButton;
    @FXML
    private Button playButton;
    @FXML
    private ImageView title;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private Stage primaryStage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    void onExitClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("You are going to exit the game!!");
        alert.setContentText("Are you sure want to exit?");
        if(alert.showAndWait().get() == ButtonType.OK){
            primaryStage = (Stage) menuPane.getScene().getWindow();
            primaryStage.close();
        }
    }

    @FXML
    void onPlayClick(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PlayerNames.fxml")));
            primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Names.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}