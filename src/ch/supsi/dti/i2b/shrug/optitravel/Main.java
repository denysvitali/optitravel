package ch.supsi.dti.i2b.shrug.optitravel;

import ch.supsi.dti.i2b.shrug.optitravel.api.MapQuest.MapQuestWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandWrapper;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {

 /*   public static void main(String [ ] args) throws IOException {
        TransitLandWrapper tr = new TransitLandWrapper();


        tr.getNearbyStops(46.0037,8.9511);
    }*/
    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("OptiTravel");
        stage.setScene(scene);
        stage.show();



        MapQuestWrapper mapQuestWrapper = new MapQuestWrapper();
        Image mapImage = mapQuestWrapper.getMapImage(46.0037, 8.9511, 7);

        ImageView imageView = new ImageView();
        imageView.setImage(mapImage);

        root.getChildren().add(imageView);



        GoogleMapView mapView = new GoogleMapView();
        mapView.addMapInializedListener(() -> {
            LatLong lugano_location = new LatLong(46.0037, 8.9511);
            MapOptions mapOptions = new MapOptions();

            mapOptions.center(lugano_location)
                    .mapType(MapTypeIdEnum.ROADMAP)
                    .overviewMapControl(false)
                    .panControl(false)
                    .rotateControl(false)
                    .scaleControl(false)
                    .streetViewControl(false)
                    .zoomControl(false)
                    .zoom(12);
            mapView.createMap(mapOptions);


        });
        root.getChildren().add(mapView);


    }
}
