package ch.supsi.dti.i2b.shrug.optitravel;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {
    final private TransitLandAPIWrapper transitLandAPIWrapper = new TransitLandAPIWrapper();
    List<Stop> stopsInBBox = new ArrayList<>();
    List<RouteStopPattern> routeStopPatternsInBBox = new ArrayList<>();
    List<ScheduleStopPair> scheduleStopPairsInBBox = new ArrayList<>();
    int count = 0;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
        Scene scene = new Scene(root, 1000, 800);

        stage.setTitle("OptiTravel");
        stage.setScene(scene);
        stage.show();


        AnchorPane ap = (AnchorPane) root.lookup("#ap-mapview");
        TextField partenza = (TextField) root.lookup("#tf_partenza");
        TextField arrivo = (TextField) root.lookup("#tf_arrivo");

        partenza.setText("Saronno");
        arrivo.setText("Milano, Stazione Centrale");


        GoogleMapView mapView = new GoogleMapView();
        ap.getChildren().add(mapView);
        //mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");
/*
        ArrayList<Stop> stops = new ArrayList<>();

        try {
/*          // Manno, La Monda
            stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(46.0248152,8.9174549)).get(0));
            // Manno, Suglio
            stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(46.0311802,8.9218289)).get(0));
*/
/*
           //Gerra Piano Paese
            stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(46.174372,8.911756)).get(0));
            //Locarno stazione
            stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(46.172491,8.800491)).get(0));
*/
           //milano
//           List<Stop> thestops = transitLandAPIWrapper.getStopsNear(new GPSCoordinates(45.485188, 9.202954),250);
//           stops.add(thestops.get(0));
            //saronno
//           stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(45.625286,9.030723),250).get(0));

/*
            stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(37.780389, -122.477560),250).get(0));

            stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(37.786391, -122.408333),250).get(0));
*/
/*

        } catch (TransitLandAPIError transitLandAPIError) {
            transitLandAPIError.printStackTrace();
        }
*/
        mapView.addMapInializedListener(() -> {
/*
            transitLandAPIWrapper.AgetRouteStopPatternsByStopsVisited(stops, (rsp)->{
                Platform.runLater(()->{
                    updateMapWithTrip(mapView, rsp);
                });
            });
*/

            //LatLong lugano_location = new LatLong(46.0037, 8.9511);
            LatLong di_location = new LatLong(-50.6060175, 165.9640191);
            MapOptions mapOptions = new MapOptions();

            mapOptions.center(di_location)
                    .mapType(MapTypeIdEnum.ROADMAP)
                    .overviewMapControl(false)
                    .panControl(false)
                    .rotateControl(false)
                    .scaleControl(false)
                    .streetViewControl(false)
                    .zoomControl(false)
                    .zoom(12);
            mapView.createMap(mapOptions);

            /*for(Stop s : stops){
                MarkerOptions markerOptions1 = new MarkerOptions();
                markerOptions1.position(
                        new LatLong(
                                s.getCoordinates().getLatitude(),
                                s.getCoordinates().getLongitude()
                        )
                );
                markerOptions1.animation(Animation.DROP);
                Marker marker = new Marker(markerOptions1);
                mapView.getMap().addMarker(marker);
            }*/



        });
        //root.getChildren().add(mapView);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                transitLandAPIWrapper.destroy();
     //           transitLandAPIWrapper = null;
                System.exit(0);

            }
        });

    }

    private void addCircle(GoogleMapView mapView, LatLong s1, int i) {
        CircleOptions circleOpt = new CircleOptions();
        circleOpt.fillColor("#009688");
        Circle circle = new Circle(circleOpt);
        circle.setCenter(s1);
        circle.setRadius(i);
        mapView.getMap().addMapShape(circle);
    }

    private void fitMap(GoogleMapView mapView, LatLong p1, LatLong p2) {
        mapView.getMap().fitBounds(new LatLongBounds(p1, p2));
    }

    private void addMarker(GoogleMapView mapView, LatLong p1, String a) {
        MarkerOptions mOpt = new MarkerOptions();
        mOpt.position(p1);
        mOpt.label(a);
        mOpt.visible(true);

        Marker m1 = new Marker(mOpt);
        mapView.getMap().addMarker(m1);
    }

    private void drawBB(GoogleMapView mapView, LatLong p1, LatLong p2) {
        Rectangle rect = new Rectangle(new RectangleOptions().bounds(new LatLongBounds(p1, p2)));
        mapView.getMap().addMapShape(rect);
    }
}
