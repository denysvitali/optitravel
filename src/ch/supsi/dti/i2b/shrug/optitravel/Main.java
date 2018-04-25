package ch.supsi.dti.i2b.shrug.optitravel;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Geometry;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.LineString;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

public class Main extends Application {

    private TransitLandAPIWrapper transitLandAPIWrapper;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
        Scene scene = new Scene(root, 1000, 800);

        stage.setTitle("OptiTravel");
        stage.setScene(scene);
        stage.show();


        transitLandAPIWrapper = new TransitLandAPIWrapper();

        AnchorPane ap = (AnchorPane) root.lookup("#ap-mapview");
        TextField partenza = (TextField) root.lookup("#tf_partenza");
        TextField arrivo = (TextField) root.lookup("#tf_arrivo");

        partenza.setText("Saronno");
        arrivo.setText("Milano, Stazione Centrale");


        GoogleMapView mapView = new GoogleMapView();
        ap.getChildren().add(mapView);
        //mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");

        mapView.addMapInializedListener(() -> {

            LatLong p1 = new LatLong(46.04110642108188, 8.941908555123282);
            LatLong p2 = new LatLong(46.00146656736627, 8.999952743875731);
            int radius = 2000;

            LatLong lugano_location = new LatLong(46.0037, 8.9511);
            LatLong di_location = new LatLong(-50.6060175,165.9640191);
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

            LatLong s1 = new LatLong(46.01946,8.974125);
            LatLong s2 = new LatLong(46.023113,8.967738);

            mapView.getMap().setCenter(di_location);

            drawBB(mapView, p1, p2);
            addMarker(mapView, p1, "1");
            addMarker(mapView, p2, "2");    
            addMarker(mapView, s1, "S1");
            addMarker(mapView, s2, "S2");

            addCircle(mapView, s1, radius);
            addCircle(mapView, s2, radius);
            fitMap(mapView, p1, p2);


        });
        //root.getChildren().add(mapView);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                transitLandAPIWrapper.destroy();
                transitLandAPIWrapper = null;

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

    private void updateMapWithTrip(GoogleMapView mapView, List<RouteStopPattern> rsp){
        if(rsp.size() == 0){
            System.out.println("RSP is empty!");
            return;
        }

        try {
            //route stop pattern id
            List<ScheduleStopPair> a = transitLandAPIWrapper.getScheduleStopPair(rsp.get(0).getTrips().get(0));
            List<ScheduleStopPair> b = transitLandAPIWrapper.getScheduleStopPair(rsp.get(0));

            List<RouteStopPattern> c = transitLandAPIWrapper.getRouteStopPatterns(rsp.get(0).getTrips().get(0));

            List<Stop> d = transitLandAPIWrapper.getStopsByRoute(rsp.get(0).getRoute().getId());
            System.out.println(rsp);
            //rsp.get(0).getTrips().stream().forEach(System.out::println);
            rsp.get(0).getStopPattern().forEach(System.out::println);
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
