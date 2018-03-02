package ch.supsi.dti.i2b.shrug.optitravel;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Polygon;
import com.lynden.gmapsfx.shapes.PolygonOptions;
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


        TransitLandAPIWrapper transitLandAPIWrapper = new TransitLandAPIWrapper();
        try {
            ArrayList<Stop> stops = transitLandAPIWrapper.getStopsByRoute("r-u0nmf-449");
            for(Stop s : stops){
                System.out.println(s.getName());
                System.out.println(s.getOperators().get(0).getName());
                System.out.println(s.getCoordinates().getLatitude() + "," + s.getCoordinates().getLongitude());
            }
        } catch (TransitLandAPIError transitLandAPIError) {
            transitLandAPIError.printStackTrace();
        }


        GoogleMapView mapView = new GoogleMapView();
        //mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");
        mapView.addMapInializedListener(() -> {
            LatLong lugano_location = new LatLong(46.0037, 8.9511);
            LatLong lucern_location = new LatLong(47.0502, 8.3093);
            LatLong zurich_location = new LatLong(47.3769,8.5417);
            LatLong geneva_location = new LatLong(46.2044, 6.1432);
            LatLong chur_location = new LatLong(46.8508, 9.5320);
            MapOptions mapOptions = new MapOptions();

            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(lugano_location);
            markerOptions1.animation(Animation.DROP);

            Marker lugano_marker = new Marker(markerOptions1);

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

            LatLong poly1 = new LatLong(
                    lugano_location.getLatitude(),
                    lugano_location.getLongitude());
            LatLong poly2 = new LatLong(
                    chur_location.getLatitude(),
                    chur_location.getLongitude());
            LatLong poly3 = new LatLong(
                    zurich_location.getLatitude(),
                    zurich_location.getLongitude());
            LatLong poly4 = new LatLong(
                    geneva_location.getLatitude(),
                    geneva_location.getLongitude());
            LatLong[] pAry = new LatLong[]{poly1, poly2, poly3, poly4};
            MVCArray pmvc = new MVCArray(pAry);

            PolygonOptions polygOpts = new PolygonOptions()
                    .paths(pmvc)
                    .strokeColor("green")
                    .strokeWeight(2)
                    .editable(false)
                    .fillColor("lightGreen")
                    .fillOpacity(0.5);

            Polygon pg = new Polygon(polygOpts);

            //mapView.getMap().panToBounds(new LatLongBounds(lugano_location, chur_location, zurich_location, geneva_location));

            mapView.getMap().addMapShape(pg);


            mapView.getMap().addMarker(lugano_marker);
        });
        root.getChildren().add(mapView);

    }
}
