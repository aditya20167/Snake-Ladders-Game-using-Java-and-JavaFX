import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Snakes & Ladders!!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
        }
    } 
 
    public static void main(String[] args) {
        launch(args);
    }
}