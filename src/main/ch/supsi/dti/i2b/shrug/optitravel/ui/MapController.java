package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Location;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.RouteType;
import ch.supsi.dti.i2b.shrug.optitravel.models.TimedLocation;
import ch.supsi.dti.i2b.shrug.optitravel.models.WalkingTrip;
import ch.supsi.dti.i2b.shrug.optitravel.models.plan.PlanSegment;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import javafx.application.Platform;

import java.util.List;
import java.util.stream.Collectors;

public class MapController implements MapComponentInitializedListener {

    private final GoogleMapView mapView;
    private GoogleMap map;
    private DirectionsService directionsService;


    private Marker origin;
    private Marker destination;

    public void addComputedDirections(PlanSegment ps) {
    	TimedLocation prevLocation = null;

    	MVCArray mva = new MVCArray(ps.getElements().stream().map(e-> new LatLong(e.getCoordinate().getLat(),
				e.getCoordinate().getLng())).toArray());
		PolylineOptions line_opts = new PolylineOptions();

		String color= ps.getTrip().getRoute().getColor();
		RouteType rc = ps.getTrip().getRoute().getType();
		if(rc != null) {
			rc = rc.getRouteCategory();
		} else {
			rc = RouteType.BUS_SERVICE;
		}
		if(color == null){
			switch(rc){
				case RAILWAY_SERVICE:
					color = "#f44336";
					break;
				case UNDERGROUND_SERVICE:
					color = "#673AB7";
					break;
				default:
					color = "#607D8B";
			}
		}

		line_opts.strokeColor(color);
		line_opts.strokeWeight(5);
    	Polyline pl = new Polyline(line_opts);
    	pl.setPath(mva);

        map.addMapShape(pl);
    }

	public void clearDirections() {

	}

	public enum NodeType {ORIGIN, DESTINATION}

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

    public void fitToBounds(Coordinate origin, Coordinate dest) {
        LatLong sw = new LatLong(Math.max(origin.getLat(), dest.getLat()), Math.min(origin.getLng(), dest.getLng()));
        LatLong ne = new LatLong(Math.min(origin.getLat(), dest.getLat()), Math.max(origin.getLng(), dest.getLng()));
        map.fitBounds(new LatLongBounds(sw, ne));
    }

    public void addDirections(Coordinate from, Coordinate to, TravelModes travelMode) {
        if (directionsService == null) directionsService = new DirectionsService();
//        if(directionsService.renderer != null) directionsService.renderer.clearDirections();

        DirectionsRequest request = new DirectionsRequest(from.toString(), to.toString(), travelMode);
        directionsService.getRoute(request, null,
                new DirectionsRenderer(true, map, mapView.getDirec()));
    }

}