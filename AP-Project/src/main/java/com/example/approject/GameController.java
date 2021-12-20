package com.example.approject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Random;

public class GameController extends PlayerNames{

    @FXML
    private ImageView background;

    @FXML
    private ImageView bluedie;

    @FXML
    private ImageView board1;

    @FXML
    private Button diceRoller;

    @FXML
    private ImageView dieImg;

    @FXML
    private ImageView dieouter;

    @FXML
    private ImageView greendie;

    @FXML
    private ImageView player1;

    @FXML
    private ImageView player2;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;

    @FXML
    private Image die1, die2, die3, die4, die5, die6;

    static Random rand = new Random();

    static int x;
    @FXML
    public void setPlayerNames(String player1, String player2){
        player1Label.setText(player1);
        player2Label.setText(player2);
    }

    @FXML
    public void setBoard(Image board) throws FileNotFoundException {
        board1.setImage(board);
    }

    @FXML
    void onClickReturn(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PlayerNames.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setStart() throws FileNotFoundException {
        int firstRoll = rand.nextInt(6) + 1;
        InputStream firstStream = new FileInputStream("C:/Users/adity/Documents/GitHub/AP_Project/AP-Project/src/main/resources/com/example/approject/dice_" + firstRoll + ".png");
        Image firstDie = new Image(firstStream);
        dieImg.setImage(firstDie);
    }

    @FXML
    void onDieRoll(ActionEvent event) throws FileNotFoundException {
        diceRoller.setDisable(true);
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    int nextRoll = rand.nextInt(6) + 1;
                    InputStream stream = new FileInputStream("C:/Users/adity/Documents/GitHub/AP_Project/AP-Project/src/main/resources/com/example/approject/dice_" + nextRoll + ".png");
                    Image diceRoll = new Image(stream);
                    dieImg.setImage(diceRoll);
                    Thread.sleep(100);
                }
                diceRoller.setDisable(false);
            }
            catch (Exception e){
                System.out.println(e);
            }
        });
        thread.start();
    }

}
