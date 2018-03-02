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

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("OptiTravel");
        stage.setScene(scene);
        stage.show();


        TransitLandAPIWrapper transitLandAPIWrapper = new TransitLandAPIWrapper();
        ArrayList<Stop> stops;
        ArrayList<RouteStopPattern> rsp;
        try {
            //stops = transitLandAPIWrapper.getStopsByRoute("r-u0nmf-449");
            stops = transitLandAPIWrapper.getStopsNear(new GPSCoordinates(46.019421,8.974028));
            for(Stop s : stops){
                System.out.println(s.getName());
                System.out.println(s.getId());
                System.out.println(s.getRoutes().get(0).getId());
                System.out.println(s.getOperators().get(0).getName());
                System.out.println(s.getCoordinates().getLatitude() + "," + s.getCoordinates().getLongitude());
            }
            rsp = transitLandAPIWrapper.getRouteStopPatterns("131270");

        } catch (TransitLandAPIError transitLandAPIError) {
            transitLandAPIError.printStackTrace();
            return;
        }


        GoogleMapView mapView = new GoogleMapView();
        //mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");
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

            for(Stop s : stops){
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
            }


            Polyline polyline = new Polyline();
            Geometry geom = rsp.get(0).getGeometry();
            ArrayList<GPSCoordinates> path = geom.getPath();

            LatLong[] ary = new LatLong[path.size()];
            MVCArray mvcArray;

            for(int i = 0; i<path.size(); i++){
                ary[i] = new LatLong(path.get(i).getLatitude(), path.get(i).getLongitude());
            }

            mvcArray = new MVCArray(ary);

            PolylineOptions polyOpts = new PolylineOptions()
                    .path(mvcArray)
                    .strokeColor("red")
                    .strokeWeight(2);


            Polyline poly = new Polyline(polyOpts);
            mapView.getMap().addMapShape(poly);


        });
        root.getChildren().add(mapView);

    }
}
