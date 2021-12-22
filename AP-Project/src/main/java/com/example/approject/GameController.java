package com.example.approject;

import javafx.animation.PathTransition;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.catalog.CatalogManager;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class GameController {

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
    private Image blueDie, greenDie;

    @FXML
    TranslateTransition translate = new TranslateTransition();
    static Random rand = new Random();

    public GameController() {
        try {
            InputStream a = new FileInputStream("AP-Project/src/main/resources/com/example/approject/blue_token.png");
            blueDie = new Image(a);
            InputStream b = new FileInputStream("AP-Project/src/main/resources/com/example/approject/green_token.png");
            greenDie = new Image(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private int x;

    @FXML
    private Text Status;
    @FXML
    public void getCoordinates(MouseEvent event){
        Status.setText("X: "+event.getX()+" Y: "+event.getY());
    }
    @FXML
    public void setPlayerNames(String player1, String player2) {
        player1Label.setText(player1);
        player2Label.setText(player2);
    }

    @FXML
    void onClickReturn(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Return to Main Menu");
            alert.setHeaderText("You are going to exit the game!!");
            alert.setContentText("Are you sure want to exit?");
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
            if (alert.showAndWait().get() == ButtonType.OK) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    static int posBlue = 1;
    static int posGreen = 1;

    public static int getPosBlue() {
        return posBlue;
    }

    public static void setPosBlue() {
        posBlue+=1;
    }

    public static int getPosGreen() {
        return posGreen;
    }

    public static void setPosGreen() {
        posGreen+=1;
    }

    @FXML
    void onDieRoll(ActionEvent event) {
        diceRoller.setDisable(true);
        Thread th1 = new Thread(()->{
                try{
                    for (int i = 0; i < 20; i++) {
                        int dieRoll = rand.nextInt(6) + 1;
                        InputStream stream = new FileInputStream("AP-Project/src/main/resources/com/example/approject/dice_" + dieRoll + ".png");
                        Image image = new Image(stream);
                        dieImg.setImage(image);
                        Thread.sleep(50);
                        x = dieRoll;
                    }
                    diceRoller.setDisable(false);
                    /*
                    while(){
                        if(blue){
                        moveblue;}
                        else if(green){
                        movegreen;}
                    }
                     */
//                    moveBlue(x);
                    PlayerBlue blue = new PlayerBlue(getX(), bluedie, rowBlue, posBlue, blueMoves);
                    PlayerGreen green = new PlayerGreen(getX(), greendie, rowGreen, posGreen, greenMoves);
                    Thread play1 = new Thread(blue);
                    Thread play2 = new Thread(green);
                    while (getPosBlue()!=100 || getPosGreen()!=100){
                        if(blueMoves && !greenMoves){
                            play1.start();
                        }
                        else if(greenMoves && !blueMoves){
                            play2.start();
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }
        });
        th1.start();

    }

    static int rowBlue = 1;
    static int rowGreen = 1;

    public static int getRowGreen() {
        return rowGreen;
    }

    public static void setRowGreen() {
        rowGreen+=1;
    }

    public static int getRowBlue() {
        return rowBlue;
    }

    public static void setRowBlue() {
        rowBlue+=1;
    }

    HashMap<Integer, Integer> snakes = new HashMap<>();
    HashMap<Integer, Integer> ladders = new HashMap<>();

    void setHashMap() {
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
    }

    static boolean blueMoves = true;
    static boolean greenMoves = false;


    public TranslateTransition getTranslate(){
        return this.translate;
    }

    public Button getDiceRoller() {
        return diceRoller;
    }

    public ImageView getDieImg(){
        return this.dieImg;
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }
}

class PlayerBlue implements Runnable{
    int moveBy;
    ImageView image;
    int row;
    int pos;
    boolean moves;

    PlayerBlue(int moveBy, ImageView image, int row, int pos, boolean moves){
        this.moveBy = moveBy;
        this.image = image;
        this.row = row;
        this.pos = pos;
        this.moves = moves;
    }

    public void movePlayerTokenX (ImageView image, int row){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setCycleCount(1);
        if(row%2==0){
            translate.setByX(-28.8);
        }
        else {
            translate.setByX(28.8);
        }
        translate.play();
    }

    public void movePlayerTokenY (ImageView image){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setCycleCount(1);
        translate.setByY(-36);
        translate.play();
    }

    @Override
    public void run(){
        int ctr = moveBy;
        while (ctr!=0){
            if(GameController.getPosBlue()%10!=0){
                movePlayerTokenX(image, GameController.getRowBlue());
                GameController.setPosBlue();
            }
            else {
                movePlayerTokenY(image);
                GameController.setPosBlue();
                GameController.setRowBlue();
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctr-=1;
            System.out.print("Pos of Blue: " + GameController.getPosBlue());
            System.out.print("\tRow of Blue: " + GameController.getRowBlue());
            System.out.println();
        }
        GameController.blueMoves = false;
        GameController.greenMoves = true;
    }
}


class PlayerGreen implements Runnable{
    int moveBy;
    ImageView image;
    int row;
    int pos;
    boolean moves;

    PlayerGreen(int moveBy, ImageView image, int row, int pos, boolean moves){
        this.moveBy = moveBy;
        this.image = image;
        this.row = row;
        this.pos = pos;
        this.moves = moves;
    }

    public void movePlayerTokenX (ImageView image, int row){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setCycleCount(1);
        if(row%2==0){
            translate.setByX(-28.8);
        }
        else {
            translate.setByX(28.8);
        }
        translate.play();
    }

    public void movePlayerTokenY (ImageView image){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setCycleCount(1);
        translate.setByY(-36);
        translate.play();
    }

    @Override
    public void run(){
        int ctr = moveBy;
        while (ctr!=0){
            if(GameController.getPosGreen()%10!=0){
                movePlayerTokenX(image, GameController.getRowGreen());
                GameController.setPosGreen();
            }
            else {
                movePlayerTokenY(image);
                GameController.setPosGreen();
                GameController.setRowGreen();
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctr-=1;
            System.out.print("pos: " + GameController.getPosGreen());
            System.out.print("\tRow: " + GameController.getRowGreen());
            System.out.println();
        }
        GameController.blueMoves = true;
        GameController.greenMoves = false;
    }
}