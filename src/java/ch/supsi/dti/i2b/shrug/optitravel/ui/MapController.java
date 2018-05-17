package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Location;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderRequest;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

import java.util.Arrays;

public class MapController implements MapComponentInitializedListener {

    private final GoogleMapView mapView;
    private GoogleMap map;
    private GeocodingService geocodingService;

    private Marker origin;
    private Marker destination;

    private final MainController mainController;

    public enum NodeType {ORIGIN, DESTINAION, TRANSIT}

    public MapController(GoogleMapView mapView, MainController mainController) {
        this.mapView = mapView;
        this.mainController = mainController;
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

        map = mapView.createMap(options);
        geocodingService = new GeocodingService();
    }

    public void addMarker(Location location, NodeType type) {
        addMarker(new LatLong(location.getLatitude(), location.getLongitude()), type);
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
            case DESTINAION:
                if (destination != null) map.removeMarker(destination);
                destination = new Marker(opt);
                map.addMarker(destination);
                map.panTo(where);
                break;
        }

    }
}