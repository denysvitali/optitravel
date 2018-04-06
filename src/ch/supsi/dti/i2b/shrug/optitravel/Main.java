package ch.supsi.dti.i2b.shrug.optitravel;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @FXML
    private GoogleMapView mapView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/main.fxml"));
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