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
        //root.getChildren().add(mapView);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                transitLandAPIWrapper.destroy();
     //           transitLandAPIWrapper = null;
                System.exit(0);

            }
        });

        //When the 2 coordinates used for creating the bbox are determined, start this Thread.
        Runnable r = ()->{
            transitLandAPIWrapper.AgetStopsByBBox(new GPSCoordinates(46.197728, 8.639571), new GPSCoordinates(45.951284, 9.120199), (stops)->{

                System.out.println(stops);
                stopsInBBox = stops;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });
            transitLandAPIWrapper.AgetRouteStopPatternsByBBox(new GPSCoordinates(46.197728, 8.639571), new GPSCoordinates(45.951284, 9.120199), (rsps)->{

                System.out.println(rsps);
                routeStopPatternsInBBox = rsps;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });
            transitLandAPIWrapper.AgetScheduleStopPairsByBBox(new GPSCoordinates(46.197728, 8.639571), new GPSCoordinates(45.951284, 9.120199), (ssps)->{

                System.out.println(ssps);
                scheduleStopPairsInBBox = ssps;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });
            organize();
        };
        Thread t = new Thread(r);
        t.start();

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

    private void organize(){

        synchronized (transitLandAPIWrapper){
            while(count!=3){
                try {
                    transitLandAPIWrapper.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("ricevuti");
        System.out.println(routeStopPatternsInBBox.get(0).getId());
        System.out.println(stopsInBBox.get(0).getId());
   //     stopsInBBox = transitLandAPIWrapper.sortStops(new GPSCoordinates(/*46.004962,8.950784*/46.003135, 8.759909), stopsInBBox);
        GPSCoordinates c = new GPSCoordinates(46.164871, 8.917476);
        List<Stop> list = transitLandAPIWrapper.sortStops(c, stopsInBBox);
        list.forEach(stop -> System.out.println(stop.getName() + " - " + Distance.distance(c.asCoordinate(), stop.getCoordinate())));
        System.out.println(list.get(0).getId());
        Map<String, Stop> mappaStops = new HashMap<>();
        for(Stop stop : list){
            mappaStops.put(stop.getId(), stop);
        }
        Map<String, RouteStopPattern> mappaRsps = new HashMap<>();
        for(RouteStopPattern rsp : routeStopPatternsInBBox){
            mappaRsps.put(rsp.getId(), rsp);
        }
        Map<ScheduleStopPair, String> mappaSsps = new HashMap<>();
        for(ScheduleStopPair ssp : scheduleStopPairsInBBox){
            mappaSsps.put(ssp, ssp.getRoute_stop_pattern_onestop_id());
        }

    }
    private void updateMapWithTrip(GoogleMapView mapView, List<RouteStopPattern> rsp){
        if(rsp.size() == 0){
            System.out.println("RSP is empty!");
            return;
        }

        try {
            //route stop pattern id

            List<ScheduleStopPair> a = transitLandAPIWrapper.getScheduleStopPair(rsp.get(0).getTrips().get(0));
            List<ScheduleStopPair> b = transitLandAPIWrapper.getScheduleStopPair(rsp.get(0),2,2, 2018/*,"07:00:00","10:00:00"*/);

 //         List<RouteStopPattern> c = transitLandAPIWrapper.getRouteStopPatterns(rsp.get(0).getTrips().get(0));

            List<Stop> d = transitLandAPIWrapper.getStopsByRoute(rsp.get(0).getRoute().getId());

            long t1 = System.currentTimeMillis();
            transitLandAPIWrapper.AgetRouteStopPatternsByBBox(new GPSCoordinates(37.668,-122.000), new GPSCoordinates(37.719,-122.500), routeStopPatterns -> {

                long t2 = System.currentTimeMillis();
                System.out.println(t2-t1+" routeStopPatterns");
            });
            transitLandAPIWrapper.AgetStopsByBBox(new GPSCoordinates(37.668,-122.000), new GPSCoordinates(37.719,-122.500), stops -> {

                long t2 = System.currentTimeMillis();
                System.out.println(t2-t1+" stops");
            });

            System.out.println(rsp);
            //rsp.get(0).getTrips().stream().forEach(System.out::println);
            rsp.get(2).getStopPattern().stream().forEach(System.out::println);
            Polyline polyline = new Polyline();
            Geometry geom = rsp.get(2).getGeometry();
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
