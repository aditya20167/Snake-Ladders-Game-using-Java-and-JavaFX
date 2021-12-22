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

    static int posBlue = 1;
    static int posGreen = 1;

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
    public static int snl(ImageView image, int pos){
        int pos1 = 0;
        PathTransition pathTransition = new PathTransition();
        TranslateTransition t = new TranslateTransition();
        Polyline polyline = new Polyline();
        Path path = new Path();
        if(snakes.containsKey(pos)){
            pos1 = snakes.get(pos) - pos;
            if (pos == 24) {
                polyline.getPoints().addAll(167.2, 336.8, 146.4, 367.2, 168.1, 379.6, 161.6, 384.8);
            }
            else if (pos == 43) {
                polyline.getPoints().addAll(93.6, 265.6, 114.4, 294.4, 93.6, 305.6, 91.2, 317.6);
            }
            else if (pos == 56) {
                polyline.getPoints().addAll(177.6, 241.6, 162.4, 268.8, 175.2, 295.2, 163.2, 316.8);
            }
            else if (pos == 60) {
                polyline.getPoints().addAll(91.2, 221.6, 69.6, 243.2, 82.4, 241.2, 92.8, 247.2);
            }
            else if (pos == 69) {
                polyline.getPoints().addAll(240.8, 190.4, 263.2, 222.4, 241.6, 234.4, 237.6, 247.2);
            }
            else if (pos == 86) {
                polyline.getPoints().addAll(223.2, 124.2, 192.8, 164.4, 235.2, 190.4, 235.2, 214.4);
            }
            else if (pos == 90) {
                polyline.getPoints().addAll(264.8, 118.4, 287.2, 141.8, 274.4, 147.2, 262.4, 142.4);
            }
            else if (pos == 94) {
                polyline.getPoints().addAll(247.2, 87.2, 225.6, 119.2, 247.2, 129.6, 238.4, 141.6);
            }
            else if (pos == 96) {
                polyline.getPoints().addAll(142.4, 84.2, 164.8, 104.8, 152.8, 112.8, 139.2, 106.4);
            }
            else if (pos == 98) {
                polyline.getPoints().addAll(109.6, 95.2, 123.2, 138.4, 112.2, 173.8, 115.2, 214.4);
            }
            pathTransition.setPath(polyline);
        }
        else if(ladders.containsKey(pos)){
            pos1 = ladders.get(pos) - pos;
//            path.getElements().add(new MoveTo(image.getX(), image.getY()));
            if (pos == 3) {
                t.setToX(64.0);
                t.setToY(312.0);
            }
            else if (pos == 8) {
                t.setToX(191.2);
                t.setToY(246.4);
            }
            else if (pos == 16) {
                t.setToX(189.6);
                t.setToY(316.8);
            }
            else if (pos == 29) {
                t.setToX(242.4);
                t.setToY(282.4);
            }
            else if (pos == 37) {
                path.getElements().add(new LineTo(164.8, 178.4));
            }
            else if (pos == 50) {
                path.getElements().add(new LineTo(288.8, 177.6));
            }
            else if (pos == 61) {
                path.getElements().add(new LineTo(89.6, 108.0));
            }
            else if (pos == 64) {
                path.getElements().add(new LineTo(138.4, 139.2));
            }
            else if (pos == 76) {
                path.getElements().add(new LineTo(187.2, 72.0));
            }
            else if (pos == 89) {
                path.getElements().add(new LineTo(288.8, 69.6));
            }
            pathTransition.setPath(path);
        }
        pathTransition.setDuration(Duration.seconds(1));
        pathTransition.setNode(image);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pathTransition.play();
        t.setDuration(Duration.millis(1000));
        t.setNode(image);
        t.play();
        return pos1;
    }
    public static void movePlayerTokenX(ImageView image, int row){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
//        translate.setCycleCount(1);
        if(row%2==0){
            translate.setByX(-28.8);
        }
        else {
            translate.setByX(28.8);
        }
        translate.play();
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
            }
        }
        else if (flag == 1) {
            while (ctr != 0) {
                if (GameController.getPosBlue() % 10 != 0) {
                    GameController.movePlayerTokenX(image, GameController.getRowBlue());
                    GameController.setPosBlue(1);
                } else {
                    GameController.movePlayerTokenY(image);
                    GameController.setPosBlue(1);
                    GameController.setRowBlue();
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctr -= 1;
                System.out.print("Pos of Blue: " + GameController.getPosBlue());
                System.out.print("\tRow of Blue: " + GameController.getRowBlue());
                System.out.println();
            }
            if (GameController.getSnakes().containsKey(GameController.getPosBlue()) || GameController.getLadders().containsKey(GameController.getPosBlue())) {
                GameController.setPosBlue(GameController.snl(image, GameController.getPosBlue()));
            }
            GameController.blueMoves = false;
            GameController.greenMoves = true;
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
                translate.setByY(-30);
                translate.play();
                flag = 1;
            }
        }
        else if (flag == 1) {
            while (ctr != 0) {
                if (GameController.getPosGreen() % 10 != 0) {
                    GameController.movePlayerTokenX(image, GameController.getRowGreen());
                    GameController.setPosGreen(1);
                } else {
                    GameController.movePlayerTokenY(image);
                    GameController.setPosGreen(1);
                    GameController.setRowGreen();
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctr -= 1;
                System.out.print("pos: " + GameController.getPosGreen());
                System.out.print("\tRow: " + GameController.getRowGreen());
                System.out.println();
            }
            if (GameController.getSnakes().containsKey(GameController.getPosGreen()) || GameController.getLadders().containsKey(GameController.getPosGreen())) {
                GameController.setPosGreen(GameController.snl(image, GameController.getPosGreen()));
            }
            GameController.blueMoves = true;
            GameController.greenMoves = false;
        }
    }
}