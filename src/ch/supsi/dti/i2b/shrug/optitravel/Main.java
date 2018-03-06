package ch.supsi.dti.i2b.shrug.optitravel;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results.RouteStopPattern;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private TransitLandAPIWrapper transitLandAPIWrapper;

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("OptiTravel");
        stage.setScene(scene);
        stage.show();


        transitLandAPIWrapper = new TransitLandAPIWrapper();



        GoogleMapView mapView = new GoogleMapView();
        //mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");

        ArrayList<Stop> stops = new ArrayList<>();
        try {
            // Manno, La Monda
            stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(46.0248152,8.9174549)).get(0));
            // Manno, Suglio
            stops.add(transitLandAPIWrapper.getStopsNear(new GPSCoordinates(46.0311802,8.9218289)).get(0));
        } catch (TransitLandAPIError transitLandAPIError) {
            transitLandAPIError.printStackTrace();
        }

        mapView.addMapInializedListener(() -> {

            /*transitLandAPIWrapper.AgetRouteStopPatternsByStopsVisited(stops, (rsp)->{
                Platform.runLater(()->{
                    updateMapWithTrip(mapView, rsp);
                });
            });*/

            LatLong lugano_location = new LatLong(46.0037, 8.9511);
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

    private void updateMapWithTrip(GoogleMapView mapView, ArrayList<RouteStopPattern> rsp){
        if(rsp.size() == 0){
            System.out.println("RSP is empty!");
            return;
        }

        try {

            System.out.println(rsp);
            //rsp.get(0).getTrips().stream().forEach(System.out::println);
            rsp.get(0).getStopPattern().stream().forEach(System.out::println);
            Polyline polyline = new Polyline();
            Geometry geom = rsp.get(0).getGeometry();
            LineString path = null;
            path = geom.getLineString();

            LatLong[] ary = new LatLong[path.size()];
            MVCArray mvcArray;
            LatLongBounds bounds = new LatLongBounds();

            for (int i = 0; i < path.size(); i++) {
                ary[i] = new LatLong(path.get(i).getLatitude(), path.get(i).getLongitude());
                bounds.extend(ary[i]);
            }

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


            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(
                    new LatLong(
                            ary[0].getLatitude(),
                            ary[0].getLongitude()
                    )
            );
            markerOptions1.animation(Animation.DROP);
            Marker marker_start = new Marker(markerOptions1);

            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.position(
                    new LatLong(
                            ary[path.size() - 1].getLatitude(),
                            ary[path.size() - 1].getLongitude()
                    )
            );
            markerOptions2.animation(Animation.DROP);
            Marker marker_end = new Marker(markerOptions2);

            //mapView.getMap().addMarker(marker_start);
            mapView.getMap().addMarker(marker_end);
        } catch (TransitLandAPIError transitLandAPIError) {
            transitLandAPIError.printStackTrace();
        }
    }
}
