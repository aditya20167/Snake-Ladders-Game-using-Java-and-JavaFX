package com.example.approject;

import javafx.animation.SequentialTransition;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class GameController extends PlayerNames{

    @FXML
    private ImageView arrow;
    @FXML
    private ImageView bluedie;
    @FXML
    private Button diceRoller;
    @FXML
    private ImageView dieImg;
    @FXML
    private ImageView greendie;
    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    @FXML
    private Pane pane;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private Text Status;
    @FXML
    TranslateTransition translate = new TranslateTransition();
    static Random rand = new Random();
    private int x;
    private String bluePlayer;
    private String greenPlayer;
    private static int posBlue = 1;
    private static int posGreen = 1;

    @FXML
    public void getCoordinates(MouseEvent event){
        Status.setText("X: "+event.getX()+" Y: "+event.getY());
    }

    @FXML
    public void setPlayerNames(String player1, String player2) {
        player1Label.setText(player1);
        player2Label.setText(player2);
        setBluePlayer(player1);
        setGreenPlayer(player2);

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
                posBlue = 0;
                posGreen = 0;
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
        setHashMap();
        int firstRoll = rand.nextInt(6) + 1;
        InputStream firstStream = new FileInputStream("AP-Project/src/main/resources/com/example/approject/dice_" + firstRoll + ".png");
        Image firstDie = new Image(firstStream);
        dieImg.setImage(firstDie);
    }
    public static int getPosBlue() {
        return posBlue;
    }
    public static void setPosBlue(int x) {
        posBlue+=x;
    }
    public static int getPosGreen() {
        return posGreen;
    }
    public static void setPosGreen(int x) {
        posGreen+=x;
    }

    public String getBluePlayer() {
        return bluePlayer;
    }

    public void setBluePlayer(String bluePlayer) {
        this.bluePlayer = bluePlayer;
        System.out.println(bluePlayer);
    }

    public String getGreenPlayer() {
        return greenPlayer;
    }

    public void setGreenPlayer(String greenPlayer) {
        this.greenPlayer = greenPlayer;
        System.out.println(greenPlayer);
    }

    public String getWinner(){
        if(posGreen==100 && posBlue!=100){
            return getGreenPlayer();
        }
        return getBluePlayer();
    }

    @FXML
    void onDieRoll() {
        diceRoller.setDisable(true);
        Thread th1 = new Thread(()->{
                try{
                    for (int i = 0; i < 15; i++) {
                        int dieRoll = rand.nextInt(6) + 1;
                        InputStream stream = new FileInputStream("AP-Project/src/main/resources/com/example/approject/dice_" + dieRoll + ".png");
                        Image image = new Image(stream);
                        dieImg.setImage(image);
                        Thread.sleep(50);
                        x = dieRoll;
                    }
                    diceRoller.setDisable(false);
                    PlayerBlue blue = new PlayerBlue(getX(), bluedie);
                    PlayerGreen green = new PlayerGreen(getX(), greendie);
                    Thread play1 = new Thread(blue);
                    Thread play2 = new Thread(green);
                    if(blueMoves && !greenMoves){
                        play1.start();
                    }
                    else {
                        play2.start();
                    }


                }
                catch (Exception e) {
                    e.printStackTrace();
                }
        });
        th1.start();
        if(posBlue==100 || posGreen==100){
            String winner = getWinner();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("The game has ended");
            alert.setHeaderText("The winner is " + winner);
            alert.setContentText("Click OK to exit the game");
            if(alert.showAndWait().get() == ButtonType.OK){
                stage = (Stage) pane.getScene().getWindow();
                stage.close();
            }
        }
    }
    static HashMap<Integer, Integer> snakes = new HashMap<>();
    static HashMap<Integer, Integer> ladders = new HashMap<>();
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
    public int getX(){
        return this.x;
    }
    public static HashMap getSnakes() {
        return snakes;
    }
    public static HashMap getLadders() {
        return ladders;
    }
    public static double[] snl(ImageView image, int pos, double x, double y){
        int pos1 = 0;
        TranslateTransition t = new TranslateTransition(Duration.millis(1000), image);
        TranslateTransition t1 = new TranslateTransition(Duration.millis(250), image);
        TranslateTransition t2 = new TranslateTransition(Duration.millis(250), image);
        TranslateTransition t3 = new TranslateTransition(Duration.millis(250), image);
        TranslateTransition t4 = new TranslateTransition(Duration.millis(250), image);
        SequentialTransition seq = new SequentialTransition(t1, t2, t3, t4);
        t1.setFromX(x);t1.setFromY(y);
        if(snakes.containsKey(pos)){
            pos1 = snakes.get(pos) - pos;
            if (pos == 24) {
                t1.setToX(x+25);t1.setToY(y+20);
                t2.setFromX(x+25);t2.setFromY(y+20);
                t2.setToX(x);t2.setToY(y+50);
                t3.setFromX(x);t3.setFromY(y+50);
                t3.setToX(x+23);t3.setToY(y+60);
                t4.setFromX(x+23);t4.setFromY(y+60);
                t4.setToX(x+15);t4.setToY(y+70);
                x+=25;
                y+=70;
            }
            else if (pos == 43) {
                t1.setToX(x-25);t1.setToY(y+20);
                t2.setFromX(x-25);t2.setFromY(y+20);
                t2.setToX(x);t2.setToY(y+50);
                t3.setFromX(x);t3.setFromY(y+50);
                t3.setToX(x-23);t3.setToY(y+60);
                t4.setFromX(x-23);t4.setFromY(y+60);
                t4.setToX(x-45);t4.setToY(y+50);
                x-=45;
                y+=50;
            }
            else if (pos == 56) {
                t1.setToX(x+10);t1.setToY(y+30);
                t2.setFromX(x+10);t2.setFromY(y+30);
                t2.setToX(x-5);t2.setToY(y+70);
                t3.setFromX(x-5);t3.setFromY(y+70);
                t3.setToX(x+4);t3.setToY(y+100);
                t4.setFromX(x+4);t4.setFromY(y+100);
                t4.setToX(x-12);t4.setToY(y+110);
                x-=12;
                y+=110;
            }
            else if (pos == 60) {
                t1.setToX(x+25);t1.setToY(y+15);
                t2.setFromX(x+25);t2.setFromY(y+15);
                t2.setToX(x);t2.setToY(y+40);
                t3.setFromX(x);t3.setFromY(y+40);
                t3.setToX(x+13);t3.setToY(y+45);
                t4.setFromX(x+13);t4.setFromY(y+45);
                t4.setToX(x+42);t4.setToY(y+40);
                x+=42;
                y+=40;
            }
            else if (pos == 69) {
                t1.setToX(x-25);t1.setToY(y+20);
                t2.setFromX(x-25);t2.setFromY(y+20);
                t2.setToX(x);t2.setToY(y+50);
                t3.setFromX(x);t3.setFromY(y+50);
                t3.setToX(x-23);t3.setToY(y+60);
                t4.setFromX(x-23);t4.setFromY(y+60);
                t4.setToX(x-50);t4.setToY(y+70);
                x-=50;
                y+=70;
            }
            else if (pos == 86) {
                t1.setToX(x+30);t1.setToY(y+30);
                t2.setFromX(x+30);t2.setFromY(y+30);
                t2.setToX(x);t2.setToY(y+60);
                t3.setFromX(x);t3.setFromY(y+60);
                t3.setToX(x+43);t3.setToY(y+90);
                t4.setFromX(x+43);t4.setFromY(y+90);
                t4.setToX(x+65);t4.setToY(y+110);
                x+=65;
                y+=110;
            }
            else if (pos == 90) {
                t1.setToX(x-25);t1.setToY(y+15);
                t2.setFromX(x-25);t2.setFromY(y+15);
                t2.setToX(x);t2.setToY(y+40);
                t3.setFromX(x);t3.setFromY(y+40);
                t3.setToX(x-13);t3.setToY(y+45);
                t4.setFromX(x-13);t4.setFromY(y+45);
                t4.setToX(x-26);t4.setToY(y+30);
                x-=26;
                y+=30;
            }
            else if (pos == 94) {
                t1.setToX(x+25);t1.setToY(y+20);
                t2.setFromX(x+25);t2.setFromY(y+20);
                t2.setToX(x);t2.setToY(y+50);
                t3.setFromX(x);t3.setFromY(y+50);
                t3.setToX(x+23);t3.setToY(y+60);
                t4.setFromX(x+23);t4.setFromY(y+60);
                t4.setToX(x+40);t4.setToY(y+80);
                x+=40;
                y+=80;
            }
            else if (pos == 96) {
                t1.setToX(x-25);t1.setToY(y+15);
                t2.setFromX(x-25);t2.setFromY(y+15);
                t2.setToX(x);t2.setToY(y+40);
                t3.setFromX(x);t3.setFromY(y+40);
                t3.setToX(x-13);t3.setToY(y+45);
                t4.setFromX(x-13);t4.setFromY(y+45);
                t4.setToX(x-26);t4.setToY(y+40);
                x-=26;
                y+=40;
            }
            else if (pos == 98) {
                t1.setToX(x-5);t1.setToY(y+24);
                t2.setFromX(x-5);t2.setFromY(y+24);
                t2.setToX(x+5);t2.setToY(y+45);
                t3.setFromX(x+5);t3.setFromY(y+65);
                t3.setToX(x-5);t3.setToY(y+100);
                t4.setFromX(x-5);t4.setFromY(y+100);
                t4.setToX(x+21);t4.setToY(y+146);
                x+=21;
                y+=146;
            }
            seq.play();
        }
        else if(ladders.containsKey(pos)){
            pos1 = ladders.get(pos) - pos;
            if (pos == 3) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x-57.8);t.setToY(y-72.0);
                x-=57.8;
                y-=72.0;
            }
            else if (pos == 8) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x-63.8);t.setToY(y-144.0);
                x-=63.8;
                y-=144.0;
            }
            else if (pos == 16) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+13.9);t.setToY(y-36.0);
                x+=13.9;
                y-=36.0;
            }
            else if (pos == 29) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x-48.9);t.setToY(y-36.0);
                x-=48.9;
                y-=36.0;
            }
            else if (pos == 37) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+18.9);t.setToY(y-108.0);
                x+=18.9;
                y-=108.0;
            }
            else if (pos == 50) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x-43);t.setToY(y-72.0);
                x-=43;
                y-=72.0;
            }
            else if (pos == 61) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+23.9);t.setToY(y-72.0);
                x+=23.9;
                y-=72.0;
            }
            else if (pos == 64) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x);t.setToY(y-36.0);
                y-=36.0;
            }
            else if (pos == 76) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+16);t.setToY(y-72.0);
                x+=16;
                y-=72.0;
            }
            else if (pos == 89) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x-5);t.setToY(y-36.0);
                x-=5;
                y-=36.0;
            }
            t.play();
        }
        return new double[]{x, y, pos1};
    }
    public static double movePlayerTokenX(ImageView image, int row, double x){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        if(row%2==0){
            translate.setByX(28.9);
            x+=28.9;
        }
        else {
            translate.setByX(-28.9);
            x-=28.9;
        }
        translate.play();
        return x;
    }

    public static void movePlayerTokenY(ImageView image){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setByY(-36);
        translate.play();
    }
}

class PlayerBlue implements Runnable{
    int moveBy;
    ImageView image;
    static int flag = 0;
    static double x = 0.0, y = 0.0;
    PlayerBlue(int moveBy, ImageView image){
        this.moveBy = moveBy;
        this.image = image;
    }
    @Override
    public void run(){
        int ctr = moveBy;
        if (flag == 0){
            if(moveBy == 1) {
                TranslateTransition translate = new TranslateTransition();
                translate.setNode(image);
                translate.setByY(-30);
                translate.play();
                flag = 1;
                y-=36;
            }
        }
        else if (flag == 1) {
            if (GameController.getPosBlue() + moveBy <= 100) {
                while (ctr != 0) {
                    if (GameController.getPosBlue() % 10 != 0) {
                        x = GameController.movePlayerTokenX(image, GameController.getPosBlue() / 10, x);
                        GameController.setPosBlue(1);
                    } else {
                        GameController.movePlayerTokenY(image);
                        GameController.setPosBlue(1);
                        y -= 36;
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ctr -= 1;
                    System.out.print("Pos of Blue: " + GameController.getPosBlue());
                    System.out.print("\tRow of Blue: " + GameController.getPosBlue() / 10);
                    System.out.println();
                }
                System.out.println("X: " + x + " Y: " + y + " blue");
                if (GameController.getSnakes().containsKey(GameController.getPosBlue()) || GameController.getLadders().containsKey(GameController.getPosBlue())) {
                    System.out.println("X: " + x + " Y: " + y);
                    double[] arr = GameController.snl(image, GameController.getPosBlue(), x, y);
                    x = arr[0];
                    y = arr[1];
                    GameController.setPosBlue((int) arr[2]);
                }
            }
        }
        GameController.blueMoves = false;
        GameController.greenMoves = true;
    }
}

class PlayerGreen implements Runnable{
    private int moveBy;
    ImageView image;
    private static int flag = 0;
    private static double x = 0.0, y = 0.0;
    PlayerGreen(int moveBy, ImageView image){
        this.moveBy = moveBy;
        this.image = image;
    }
    @Override
    public void run(){
        int ctr = moveBy;
        if (flag == 0){
            if(moveBy == 1) {
                TranslateTransition translate = new TranslateTransition();
                translate.setNode(image);
//                translate.setCycleCount(1);
                translate.setByY(-36);
                translate.play();
                flag = 1;
                y-=36;
            }
        }
        else if (flag == 1) {
            if (GameController.getPosGreen() + moveBy <= 100) {
                while (ctr != 0) {
                    if (GameController.getPosGreen() % 10 != 0) {
                        x = GameController.movePlayerTokenX(image, GameController.getPosGreen() / 10, x);
                        GameController.setPosGreen(1);
                    } else {
                        GameController.movePlayerTokenY(image);
                        GameController.setPosGreen(1);
                        y -= 36;
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ctr -= 1;
                    System.out.print("Pos of Green: " + GameController.getPosGreen());
                    System.out.print("\tRow of Green: " + GameController.getPosGreen() / 10);
                    System.out.println();
                }
                System.out.println("X: " + x + " Y: " + y + " green");
                if (GameController.getSnakes().containsKey(GameController.getPosGreen()) || GameController.getLadders().containsKey(GameController.getPosGreen())) {
                    System.out.println("X: " + x + " Y: " + y);
                    double[] arr = GameController.snl(image, GameController.getPosGreen(), x, y);
                    x = arr[0];
                    y = arr[1];
                    GameController.setPosGreen((int) arr[2]);
                }
            }
        }
        GameController.blueMoves = true;
        GameController.greenMoves = false;
    }
}