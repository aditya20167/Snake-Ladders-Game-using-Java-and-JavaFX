package com.example.approject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class LoadingController {

    @FXML
    private AnchorPane menuPane;

    @FXML
    private ProgressBar progress = new ProgressBar();

    @FXML
    private Button start;

    @FXML
    private ProgressIndicator progress2 = new ProgressIndicator();

    @FXML
    private Stage primaryStage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;

    public void onStartClick(ActionEvent event) {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    progress.setProgress(i / 100.0);
                    progress2.setProgress(i / 100.0);
                    Thread.sleep(25);
                }
                Platform.runLater(() -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                        root = fxmlLoader.load();
                        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Menu.css")).toExternalForm());
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
