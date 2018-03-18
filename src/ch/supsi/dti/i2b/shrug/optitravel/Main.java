package ch.supsi.dti.i2b.shrug.optitravel;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed.TransitFeedWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
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
        List<Stop> stops;
        try {
            stops = gtfsrs.getStopsByTrip("t-4840d5-domodossolastazione");
            for(Stop s : stops){
                System.out.println(String.format("%s - %s", s.getUid(), s.getName()));
            }
        } catch (GTFSrsError gtfSrsError) {
            gtfSrsError.printStackTrace();
            return;
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

            LatLongBounds bounds = new LatLongBounds();

            for(Stop s : stops){
                /*MarkerOptions markerOptions1 = new MarkerOptions();
                markerOptions1.position(
                        new LatLong(
                                s.getLat(),
                                s.getLng()
                        )
                );
                markerOptions1.animation(Animation.DROP);
                Marker marker = new Marker(markerOptions1);
                mapView.getMap().addMarker(marker);*/
                bounds.extend(new LatLong(
                        s.getLat(),
                        s.getLng()
                ));
            }

            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(
                    new LatLong(
                            stops.get(stops.size()-1).getLat(),
                            stops.get(stops.size()-1).getLng()
                    )
            );
            markerOptions1.animation(Animation.DROP);
            Marker marker = new Marker(markerOptions1);
            mapView.getMap().addMarker(marker);


            MVCArray mvcArray;
            LatLong[] ary = new LatLong[stops.size()];

            for (int i = 0; i < stops.size(); i++) {
                ary[i] = new LatLong(stops.get(i).getLat(), stops.get(i).getLng());
            }

            mapView.getMap().setCenter(ary[0]);

            mvcArray = new MVCArray(ary);


            PolylineOptions poly_opts = new PolylineOptions()
                    .path(mvcArray)
                    .strokeColor("#f1c40f")
                    .strokeWeight(4)
                    .zIndex(2);

            PolylineOptions poly_stroke_opts = new PolylineOptions()
                    .path(mvcArray)
                    .strokeColor("#999")
                    .strokeWeight(6)
                    .strokeOpacity(1)
                    .zIndex(1);


            Polyline poly = new Polyline(poly_opts);
            Polyline poly_stroke = new Polyline(poly_stroke_opts);

            mapView.getMap().addMapShape(poly);
            mapView.getMap().addMapShape(poly_stroke);
            mapView.getMap().fitBounds(bounds);


            mapView.getMap().panToBounds(bounds);

        });
        root.getChildren().add(mapView);

    }
}
