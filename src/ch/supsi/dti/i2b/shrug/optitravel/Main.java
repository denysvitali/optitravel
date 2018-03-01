package ch.supsi.dti.i2b.shrug.optitravel;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("OptiTravel");
        stage.setScene(scene);
        stage.show();

        /*MapQuestWrapper mapQuestWrapper = new MapQuestWrapper();
        Image mapImage = mapQuestWrapper.getMapImage(46.0037, 8.9511, 17, MapType.HYB);

        ImageView imageView = new ImageView();
        imageView.setImage(mapImage);
        root.getChildren().add(imageView);*/

        //Marker marker1 = new Marker.createProvided(new Marker.Provided("./"));


        /*MapView mapView = new MapView();
        mapView.setCenter(new Coordinate(46.0037, 8.9511));
        mapView.setZoom(17.0);
        mapView.setBingMapsApiKey("Ar02eGwO-GorbA5RsvRrxhDWJkY7_J5980FqcGFyTjWiBI2BFMJ254TMJ_MzbWyX");
        mapView.setMapType(MapType.BINGMAPS_ROAD);
        mapView.initialize();*/

        //mapView.addMarker(marker1);



        GoogleMapView mapView = new GoogleMapView();
        //mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");
        mapView.addMapInializedListener(() -> {
            MapOptions mapOptions = new MapOptions();

            mapOptions.center(new LatLong(46.0037, 8.9511))
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
