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
    private Image die1, die2, die3, die4, die5, die6, blueDie, greenDie;

    @FXML
    TranslateTransition translate = new TranslateTransition();
    static Random rand = new Random();

    public void setDiceImages() throws FileNotFoundException{
        die1 = new Image("dice_1.png");
        die2 = new Image("dice_2.png");
        die3 = new Image("dice_3.png");
        die4 = new Image("dice_4.png");
        die5 = new Image("dice_5.png");
        die6 = new Image("dice_6.png");
    }

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
        GameController.posBlue += 1;
    }

    public static int getRowBlue() {
        return rowBlue;
    }

    public static void setRowBlue() {
        GameController.rowBlue+=1;
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
                    Thread thread = new Thread(new Player(x, bluedie, rowBlue, posBlue));
                    thread.start();
                    Thread.sleep(200);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
        });
        th1.start();
    }


    public void movePlayerTokenX (ImageView image, int row){
//        TranslateTransition translate = new TranslateTransition();
//        translate.setNode(image);
//        translate.setDuration(Duration.millis(2000));
//        translate.setCycleCount(1);
        if(row%2==0){
//            translate.setByX(-25);
            image.setTranslateX(image.getTranslateX()-25);
        }
        else {
//            translate.setByX(25);
            image.setTranslateX(image.getTranslateX()+25);
        }
//        translate.play();
    }

    @FXML
    public void movePlayerTokenY (ImageView image){
//        TranslateTransition translate = new TranslateTransition();
//        translate.setNode(image);
//        translate.setDuration(Duration.millis(2000));
//        translate.setCycleCount(1);
        image.setTranslateY(image.getTranslateY()-25);
//        translate.play();

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

    boolean blueMoves = true;
    boolean greenMoves = false;



    static int rowBlue = 1;
    static int rowGreen = 1;

    void moveBlue(int moveBy){
        int ctr = moveBy;
        while (ctr!=0){
            if(posBlue%10!=0){
                movePlayerTokenX(bluedie, rowBlue);
                posBlue+=1;
            }
            else if(posBlue%10==0){
                movePlayerTokenY(bluedie);
                posBlue+=1;
                rowBlue+=1;
            }
            ctr-=1;
        }
        if(moveBy!=6){
            blueMoves = false;
            greenMoves = true;
        }
        else {
            blueMoves = true;
            greenMoves = false;
        }
    }

    void moveGreen(int moveBy){
        int ctr = moveBy;
        while (ctr!=0){
            if(posGreen%10!=0){
                movePlayerTokenX(greendie, rowGreen);
                posGreen+=1;
            }
            else if(posGreen%10==0){
                movePlayerTokenY(greendie);
                posGreen+=1;
                rowGreen+=1;
            }
            ctr-=1;
        }

        if(moveBy!=6){
            greenMoves = false;
            blueMoves = true;
        }
        else {
            greenMoves = true;
            blueMoves = false;
        }
    }

    public Image getDie1(){
        return this.die1;
    }
    public Image getDie2(){
        return this.die2;
    }
    public Image getDie3(){
        return this.die3;
    }
    public Image getDie4(){
        return this.die4;
    }
    public Image getDie5(){
        return this.die5;
    }
    public Image getDie6(){
        return this.die6;
    }
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

class Player implements Runnable{
    int moveBy;
    ImageView image;
    int row;
    int pos;
    boolean moves;
    boolean halt;
    Player(int moveBy, ImageView image, int row, int pos){
        this.moveBy = moveBy;
        this.image = image;
        this.row = row;
        this.pos = pos;
    }

    public void movePlayerTokenX (ImageView image, int row){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
//        translate.setDuration(Duration.millis(2000));
        translate.setCycleCount(1);
        if(row%2==0){
            translate.setByX(-30);
//            image.setTranslateX(image.getTranslateX()-25);
        }
        else {
            translate.setByX(30);
//            image.setTranslateX(image.getTranslateX()+25);
        }
        translate.play();
    }

    public void movePlayerTokenY (ImageView image){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
//        translate.setDuration(Duration.millis(2000));
        translate.setCycleCount(1);
//        image.setTranslateY(image.getTranslateY()-25);
        translate.setByY(-40);
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
            System.out.println("pos: " + GameController.getPosBlue());
            System.out.println("Row: " + GameController.getRowBlue());
        }
        if(moveBy!=6){
            moves = false;
            halt = true;
        }
        else {
            moves = true;
            halt = false;
        }
    }

    public int getPos() {
        return pos;
    }

    public void setPos() {
        this.pos+=1;
    }

    public int getRow() {
        return row;
    }

    public void setRow() {
        this.row+=1;
    }
}

class DieRoll implements Runnable{
    @Override
    public void run(){

    }
}