package com.example.approject;

import javafx.animation.PathTransition;
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
import javafx.scene.shape.*;
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

    private static int posBlue = 1;
    private static int posGreen = 1;

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

    @FXML
    void onDieRoll(ActionEvent event) {
        diceRoller.setDisable(true);
        Thread th1 = new Thread(()->{
                try{
                    for (int i = 0; i < 10; i++) {
                        int dieRoll = rand.nextInt(6) + 1;
                        InputStream stream = new FileInputStream("AP-Project/src/main/resources/com/example/approject/dice_" + dieRoll + ".png");
                        Image image = new Image(stream);
                        dieImg.setImage(image);
                        Thread.sleep(100);
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
                    PlayerBlue blue = new PlayerBlue(getX(), bluedie, rowBlue, posBlue, blueMoves);
                    PlayerGreen green = new PlayerGreen(getX(), greendie, rowGreen, posGreen, greenMoves);
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
        if(getPosBlue()==100 || getPosGreen()==100){
            //move to new scene and give option to play a new game or go to home screen.
        }
    }

    static int rowBlue = 0;
    static int rowGreen = 0;

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

    public static HashMap getSnakes() {
        return snakes;
    }
    public static HashMap getLadders() {
        return ladders;
    }
    public static double[] snl(ImageView image, int pos, double x, double y){
        int pos1 = 0;
//        PathTransition pathTransition = new PathTransition();
        TranslateTransition t = new TranslateTransition(Duration.millis(1000), image);
        TranslateTransition t1 = new TranslateTransition(Duration.millis(250), image);
        TranslateTransition t2 = new TranslateTransition(Duration.millis(250), image);
        TranslateTransition t3 = new TranslateTransition(Duration.millis(250), image);
        TranslateTransition t4 = new TranslateTransition(Duration.millis(250), image);
        SequentialTransition seq = new SequentialTransition(t1, t2, t3, t4);
        t1.setFromX(x);t1.setFromY(y);
//        Polyline polyline = new Polyline();
//        Path path = new Path();
        double x1 = x, y1 = y;
        if(snakes.containsKey(pos)){
            pos1 = snakes.get(pos) - pos;
            if (pos == 24) {
//                polyline.getPoints().addAll(167.2, 336.8, 146.4, 367.2, 168.1, 379.6, 161.6, 384.8);
                t1.setToX(x+25);t1.setToY(y+20);
                t2.setFromX(x+25);t2.setFromY(y+20);
                t2.setToX(x);t2.setToY(y+50);
                t3.setFromX(x);t3.setFromY(y+50);
                t3.setToX(x+23);t3.setToY(y+60);
                t4.setFromX(x+23);t4.setFromY(y+60);
                t4.setToX(x+20);t4.setToY(y+70);
            }
            else if (pos == 43) {
//                polyline.getPoints().addAll(93.6, 265.6, 114.4, 294.4, 93.6, 305.6, 91.2, 317.6);
                t1.setToX(x-25);t1.setToY(y+20);
                t2.setFromX(x-25);t2.setFromY(y+20);
                t2.setToX(x);t2.setToY(y+50);
                t3.setFromX(x);t3.setFromY(y+50);
                t3.setToX(x-23);t3.setToY(y+60);
                t4.setFromX(x-23);t4.setFromY(y+60);
                t4.setToX(x-20);t4.setToY(y+70);
            }
            else if (pos == 56) {
//                polyline.getPoints().addAll(177.6, 241.6, 162.4, 268.8, 175.2, 295.2, 163.2, 316.8);
                t1.setToX(x+10);t1.setToY(y+30);
                t2.setFromX(x+10);t2.setFromY(y+30);
                t2.setToX(x-5);t2.setToY(y+70);
                t3.setFromX(x-5);t3.setFromY(y+70);
                t3.setToX(x+4);t3.setToY(y+100);
                t4.setFromX(x+4);t4.setFromY(y+100);
                t4.setToX(x-3);t4.setToY(y+115);
            }
            else if (pos == 60) {
//                polyline.getPoints().addAll(91.2, 221.6, 69.6, 243.2, 82.4, 241.2, 92.8, 247.2);
                t1.setToX(x+25);t1.setToY(y+15);
                t2.setFromX(x+25);t2.setFromY(y+15);
                t2.setToX(x);t2.setToY(y+40);
                t3.setFromX(x);t3.setFromY(y+40);
                t3.setToX(x+13);t3.setToY(y+45);
                t4.setFromX(x+13);t4.setFromY(y+45);
                t4.setToX(x+26);t4.setToY(y+40);
            }
            else if (pos == 69) {
//                polyline.getPoints().addAll(240.8, 190.4, 263.2, 222.4, 241.6, 234.4, 237.6, 247.2);
                t1.setToX(x-25);t1.setToY(y+20);
                t2.setFromX(x-25);t2.setFromY(y+20);
                t2.setToX(x);t2.setToY(y+50);
                t3.setFromX(x);t3.setFromY(y+50);
                t3.setToX(x-23);t3.setToY(y+60);
                t4.setFromX(x-23);t4.setFromY(y+60);
                t4.setToX(x-20);t4.setToY(y+70);
            }
            else if (pos == 86) {
//                polyline.getPoints().addAll(223.2, 124.2, 192.8, 164.4, 235.2, 190.4, 235.2, 214.4);
                t1.setToX(x+30);t1.setToY(y+30);
                t2.setFromX(x+30);t2.setFromY(y+30);
                t2.setToX(x);t2.setToY(y+60);
                t3.setFromX(x);t3.setFromY(y+60);
                t3.setToX(x+43);t3.setToY(y+90);
                t4.setFromX(x+43);t4.setFromY(y+90);
                t4.setToX(x+50);t4.setToY(y+110);
            }
            else if (pos == 90) {
//                polyline.getPoints().addAll(264.8, 118.4, 287.2, 141.8, 274.4, 147.2, 262.4, 142.4);
                t1.setToX(x-25);t1.setToY(y+15);
                t2.setFromX(x-25);t2.setFromY(y+15);
                t2.setToX(x);t2.setToY(y+40);
                t3.setFromX(x);t3.setFromY(y+40);
                t3.setToX(x-13);t3.setToY(y+45);
                t4.setFromX(x-13);t4.setFromY(y+45);
                t4.setToX(x-26);t4.setToY(y+40);
            }
            else if (pos == 94) {
//                polyline.getPoints().addAll(247.2, 87.2, 225.6, 119.2, 247.2, 129.6, 238.4, 141.6);
                t1.setToX(x+25);t1.setToY(y+20);
                t2.setFromX(x+25);t2.setFromY(y+20);
                t2.setToX(x);t2.setToY(y+50);
                t3.setFromX(x);t3.setFromY(y+50);
                t3.setToX(x+23);t3.setToY(y+60);
                t4.setFromX(x+23);t4.setFromY(y+60);
                t4.setToX(x+20);t4.setToY(y+70);
            }
            else if (pos == 96) {
//                polyline.getPoints().addAll(142.4, 84.2, 164.8, 104.8, 152.8, 112.8, 139.2, 106.4);
                t1.setToX(x-25);t1.setToY(y+15);
                t2.setFromX(x-25);t2.setFromY(y+15);
                t2.setToX(x);t2.setToY(y+40);
                t3.setFromX(x);t3.setFromY(y+40);
                t3.setToX(x-13);t3.setToY(y+45);
                t4.setFromX(x-13);t4.setFromY(y+45);
                t4.setToX(x-26);t4.setToY(y+40);
            }
            else if (pos == 98) {
//                polyline.getPoints().addAll(109.6, 95.2, 123.2, 138.4, 112.2, 173.8, 115.2, 214.4);
                t1.setToX(x+10);t1.setToY(y+30);
                t2.setFromX(x+10);t2.setFromY(y+30);
                t2.setToX(x-5);t2.setToY(y+70);
                t3.setFromX(x-5);t3.setFromY(y+70);
                t3.setToX(x+4);t3.setToY(y+100);
                t4.setFromX(x+4);t4.setFromY(y+100);
                t4.setToX(x-3);t4.setToY(y+115);
            }
            seq.play();
        }
        else if(ladders.containsKey(pos)){
            pos1 = ladders.get(pos) - pos;
            if (pos == 3) {
                System.out.println("from x: " + x + " from y: " + y);
                t.setFromX(x);t.setFromY(y);
                t.setToX(x-57.4);t.setToY(y-72.0);
                x-=57.4;
                y-=72.0;
            }
            else if (pos == 8) {
                System.out.println("from x: " + x + " from y: " + y);
                t.setFromX(x);t.setFromY(y);
                t.setToX(x-57.4);t.setToY(y-144.0);
                x-=57.4;
                y-=144.0;
            }
            else if (pos == 16) {
                System.out.println("from x: " + x + " from y: " + y);
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+28.7);t.setToY(y-36.0);
                x+=28.7;
                y-=36.0;
            }
            else if (pos == 29) {
                System.out.println("from x: " + x + " from y: " + y);
                t.setFromX(x);t.setFromY(y);
                t.setToX(x-28.7);t.setToY(y-36.0);
                x-=28.7;
                y-=36.0;
            }
            else if (pos == 37) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+28.7);t.setToY(y-108.0);
                x+=28.7;
                y-=108.0;
            }
            else if (pos == 50) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x);t.setToY(y-72.0);
                y-=72.0;
            }
            else if (pos == 61) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+28.7);t.setToY(y-72.0);
                x+=28.7;
                y-=72.0;
            }
            else if (pos == 64) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x);t.setToY(y-36.0);
                y-=36.0;
            }
            else if (pos == 76) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+28.7);t.setToY(y-72.0);
                x+=28.7;
                y-=72.0;
            }
            else if (pos == 89) {
                t.setFromX(x);t.setFromY(y);
                t.setToX(x+28.7);t.setToY(y-36.0);
                x+=28.7;
                y-=36.0;
            }
            t.play();
        }
//        pathTransition.setDuration(Duration.seconds(1));
//        pathTransition.setNode(image);
//        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pathTransition.play();
        return new double[]{x,y, pos1};
    }
    public static double movePlayerTokenX(ImageView image, int row, double x){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
//        translate.setCycleCount(1);
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
//        translate.setCycleCount(1);
        translate.setByY(-36);
        translate.play();
    }
}

class PlayerBlue implements Runnable{
    int moveBy;
    ImageView image;
    int row;
    int pos;
    boolean moves;
    static int flag = 0;
    static double x = 0.0, y = 0.0;
    PlayerBlue(int moveBy, ImageView image, int row, int pos, boolean moves){
        this.moveBy = moveBy;
        this.image = image;
        this.row = row;
        this.pos = pos;
        this.moves = moves;
    }

    @Override
    public void run(){
        int ctr = moveBy;
        if (flag == 0){
            if(moveBy == 1) {
                TranslateTransition translate = new TranslateTransition();
                translate.setNode(image);
                translate.setCycleCount(1);
                translate.setByY(-30);
                translate.play();
                flag = 1;
                y-=36;
            }
            GameController.blueMoves = false;
            GameController.greenMoves = true;
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
                        GameController.setRowBlue();
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
                GameController.blueMoves = false;
                GameController.greenMoves = true;
            }
        }
    }
}


class PlayerGreen implements Runnable{
    int moveBy;
    ImageView image;
    int row;
    int pos;
    boolean moves;
    static int flag = 0;
    static double x = 0.0, y = 0.0;
    PlayerGreen(int moveBy, ImageView image, int row, int pos, boolean moves){
        this.moveBy = moveBy;
        this.image = image;
        this.row = row;
        this.pos = pos;
        this.moves = moves;
    }

    @Override
    public void run(){
        int ctr = moveBy;
        if (flag == 0){
            if(moveBy == 1) {
                TranslateTransition translate = new TranslateTransition();
                translate.setNode(image);
                translate.setCycleCount(1);
                translate.setByY(-36);
                translate.play();
                flag = 1;
                y-=36;
            }
            GameController.blueMoves = true;
            GameController.greenMoves = false;
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
//                    GameController.setRowGreen();
                        y -= 36;
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ctr -= 1;
                    System.out.print("pos: " + GameController.getPosGreen());
                    System.out.print("\tRow: " + GameController.getPosGreen() / 10);
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
                GameController.blueMoves = true;
                GameController.greenMoves = false;
            }
        }
    }
}