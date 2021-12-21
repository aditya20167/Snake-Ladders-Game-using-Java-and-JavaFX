package com.example.approject;

import javafx.animation.TranslateTransition;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class GameController extends PlayerNames{

    @FXML
    private ImageView background;

    @FXML
    private ImageView arrow;

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
    private Image die1, die2, die3, die4, die5, die6, blueDie, greenDie;

    @FXML
    TranslateTransition translate = new TranslateTransition();
    static Random rand = new Random();
    public GameController() {
        try {
            InputStream a = new FileInputStream("C:/Users/Jaskaran/Desktop/VS Code/AP-Assignments/AP-Project-1/AP-Project/src/main/resources/com/example/approject/blue_token.png");
            blueDie = new Image(a);
            InputStream b = new FileInputStream("C:/Users/Jaskaran/Desktop/VS Code/AP-Assignments/AP-Project-1/AP-Project/src/main/resources/com/example/approject/green_token.png");
            greenDie = new Image(b);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int y;
    @FXML
    public void setPlayerNames(String player1, String player2, int z){
        player1Label.setText(player1);
        player2Label.setText(player2);
        y = z;
    }

    @FXML
    void onClickReturn(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Return to Main Menu");
            alert.setHeaderText("You are going to exit the game!!");
            alert.setContentText("Are you sure want to exit?");
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
            if(alert.showAndWait().get() == ButtonType.OK){
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setStart() throws FileNotFoundException {
        translate.setNode(arrow);
        translate.setDuration(Duration.millis(500));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        translate.setByY(25);
        translate.play();
        int firstRoll = rand.nextInt(6) + 1;
        InputStream firstStream = new FileInputStream("AP-Project/src/main/resources/com/example/approject/dice_" + firstRoll + ".png");
        Image firstDie = new Image(firstStream);
        dieImg.setImage(firstDie);
    }

    @FXML
    void onDieRoll(ActionEvent event) {
        diceRoller.setDisable(true);
        translate.stop();
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    int nextRoll = rand.nextInt(6) + 1;
                    InputStream stream = new FileInputStream("AP-Project/src/main/resources/com/example/approject/dice_" + nextRoll + ".png");
                    Image diceRoll = new Image(stream);
                    dieImg.setImage(diceRoll);
                    Thread.sleep(30);
                    x = nextRoll;
                }
                diceRoller.setDisable(false);
//                movePlayerTokenX(bluedie, x);
//                translate.play();
            }
            catch (Exception e){
                System.out.println(e);
            }
        });
        thread.start();
    }
<<<<<<< Updated upstream
    void move(int x) throws FileNotFoundException {
        HashMap<Integer, Integer> snakes = new HashMap<>();
        HashMap<Integer, Integer> ladders = new HashMap<>();
        snakes.put(24, 5);
        snakes.put(43, 22);
        snakes.put(56, 25);
        snakes.put(60, 42);
        snakes.put(69, 48);
        snakes.put(86, 53);
        snakes.put(90, 72);
        snakes.put(94, 73);
        snakes.put(96, 84);
        snakes.put(98, 58);
        ladders.put(3, 21);
        ladders.put(8, 46);
        ladders.put(16, 26);
        ladders.put(29, 33);
        ladders.put(37, 65);
        ladders.put(50, 70);
        ladders.put(61, 82);
        ladders.put(64, 77);
        ladders.put(76, 95);
        ladders.put(89, 91);

=======
//    void move() throws FileNotFoundException {
//        InputStream a = new FileInputStream("C:/Users/Jaskaran/Desktop/VS Code/AP-Assignments/AP-Project-1/AP-Project/src/main/resources/com/example/approject/blue_token.png");
//        Image blueDie = new Image(a);
//        InputStream b = new FileInputStream("C:/Users/Jaskaran/Desktop/VS Code/AP-Assignments/AP-Project-1/AP-Project/src/main/resources/com/example/approject/green_token.png");
//        Image greenDie = new Image(b);
//    }

    @FXML
    public void movePlayerTokenX(ImageView image, int moveBy){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setDuration(Duration.millis(2000));
        translate.setCycleCount(1);
        translate.setByX(moveBy*25);
        translate.play();
    }
    @FXML
    public void movePlayerTokenY(ImageView image, int moveBy){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setDuration(Duration.millis(2000));
        translate.setCycleCount(1);
        translate.setByY(25);
        translate.play();
>>>>>>> Stashed changes
    }
    /*
     */
}
