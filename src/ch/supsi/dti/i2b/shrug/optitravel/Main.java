package ch.supsi.dti.i2b.shrug.optitravel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle locale = ResourceBundle.getBundle("locale/strings");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/main.fxml"), locale);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("optitravel");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.show();
    }
}