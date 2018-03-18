package ch.supsi.dti.i2b.shrug.optitravel;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed.TransitFeedWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    private TransitLandAPIWrapper transitLandAPIWrapper;
    private TransitFeedWrapper transitFeedWrapper;

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("OptiTravel");
        stage.setScene(scene);
        stage.show();

        transitFeedWrapper = new TransitFeedWrapper();
        transitLandAPIWrapper = new TransitLandAPIWrapper();

        /*try {
            transitFeedWrapper.getLocations();
        } catch (TransitFeedError transitFeedError) {
            transitFeedError.printStackTrace();
        }*/

        try {
            System.out.println(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(46.0059236,8.9502303)).get(0).getId());
        } catch (TransitLandAPIError transitLandAPIError) {
            transitLandAPIError.printStackTrace();
        }

        GoogleMapView mapView = new GoogleMapView();
        //mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");

        GTFSrsWrapper gtfsrs = new GTFSrsWrapper();
        try {
            List<Stop> stops = gtfsrs.getStopsByTrip("t-6ebb8e-lamone-cadempinostazione");
            for(Stop s : stops){
                System.out.println(String.format("%s - %s", s.getUid(), s.getName()));
            }
        } catch (GTFSrsError gtfSrsError) {
            gtfSrsError.printStackTrace();
        }

        mapView.addMapInializedListener(() -> {

            /*transitLandAPIWrapper.AgetRouteStopPatternsByStopsVisited(stops, (rsp)->{
                Platform.runLater(()->{
                    updateMapWithTrip(mapView, rsp);
                });
            });*/

            LatLong di_location = new LatLong(-50.6060175,165.9640191);
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
        root.getChildren().add(mapView);

    }
}
