package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Location;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import javafx.application.Platform;

import java.util.List;

public class MapController implements MapComponentInitializedListener {

    private final GoogleMapView mapView;
    private GoogleMap map;
    private DirectionsService directionsService;


    private Marker origin;
    private Marker destination;

    public enum NodeType {ORIGIN, DESTINATION, TRANSIT}

    public MapController(GoogleMapView mapView, MainController mainController) {
        this.mapView = mapView;
    }

    @Override
    public void mapInitialized() {

        MapOptions options = new MapOptions();
        options.mapType(MapTypeIdEnum.ROADMAP)
                .center(new LatLong(-50.6071131, 165.9725615))
                .zoom(13)
                .streetViewControl(false)
                .mapTypeControl(false)
                .panControl(true)
                .rotateControl(true)
                .scaleControl(true)
                .zoomControl(true)
                .minZoom(2);
        mapView.setKey("AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4");

        map = mapView.createMap(options, false);
    }

    public void addMarker(Location location, NodeType type) {
        Platform.runLater(() -> addMarker(new LatLong(location.getLatitude(), location.getLongitude()), type));
    }

    public void addMarker(LatLong where, NodeType type) {
        MarkerOptions opt = new MarkerOptions();
        opt.animation(Animation.DROP)
                .visible(true)
                .position(where);
        switch (type) {
            case ORIGIN:
                if (origin != null) map.removeMarker(origin);
                origin = new Marker(opt);
                map.addMarker(origin);
                map.panTo(where);
                break;
            case DESTINATION:
                if (destination != null) map.removeMarker(destination);
                destination = new Marker(opt);
                map.addMarker(destination);
                map.panTo(where);
                break;
        }
    }

    public void clearDirections() {
        if(directionsService != null && directionsService.renderer != null) directionsService.renderer.clearDirections();
        map.clearMarkers();
    }

    public void fitToBounds(Coordinate origin, Coordinate dest) {
        LatLong sw = new LatLong(Math.max(origin.getLat(), dest.getLat()), Math.min(origin.getLng(), dest.getLng()));
        LatLong ne = new LatLong(Math.min(origin.getLat(), dest.getLat()), Math.max(origin.getLng(), dest.getLng()));
        map.fitBounds(new LatLongBounds(sw, ne));
    }

    public void addDirections(Coordinate from, Coordinate to) {
        if (directionsService == null) directionsService = new DirectionsService();
//        if(directionsService.renderer != null) directionsService.renderer.clearDirections();

        fitToBounds(from, to);
        DirectionsRequest request = new DirectionsRequest(from.toString(), to.toString(), TravelModes.TRANSIT);
        directionsService.getRoute(request, (results, status) -> System.out.println("Directions received"),
                new DirectionsRenderer(true, map, mapView.getDirec()));
    }

    public void addDirections(Coordinate from, Coordinate to, List<Coordinate> stops) {
        if (directionsService == null) directionsService = new DirectionsService();
        if(directionsService.renderer != null) directionsService.renderer.clearDirections();

        DirectionsWaypoint[] waypoints = new DirectionsWaypoint[stops.size()];
        for(int i = 0; i < stops.size(); i++) {
            waypoints[i] = new DirectionsWaypoint(stops.get(i).toString());
        }
        fitToBounds(from, to);
        DirectionsRequest request = new DirectionsRequest(from.toString(), to.toString(), TravelModes.DRIVING, waypoints);
        directionsService.getRoute(request, (results, status) -> System.out.println("Directions received"),
                new DirectionsRenderer(true, map, mapView.getDirec()));
    }

}