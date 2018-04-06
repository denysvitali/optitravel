package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

import java.util.ArrayList;

public class Stop extends ch.supsi.dti.i2b.shrug.optitravel.models.Stop {
    private Geometry geometry;
    private String onestop_id;
    private String name;
    private ArrayList<Operator> operators_serving_stop;
    private ArrayList<Route> routes_serving_stop;

    public Stop(){

    }

    public String getId() {
        return onestop_id;
    }

    public String getName() {
        return name;
    }

    @Override
    public Coordinate getCoordinate() {
        GPSCoordinates coordinates = getCoordinates();
        if(coordinates == null){
            return null;
        }
        return new Coordinate(coordinates.getLatitude(), coordinates.getLongitude());
    }

    public ArrayList<Operator> getOperators() {
        return operators_serving_stop;
    }

    public ArrayList<Route> getRoutes() {
        return routes_serving_stop;
    }

    public GPSCoordinates getCoordinates() {
        try {
            return geometry.getCoordinates();
        } catch (TransitLandAPIError transitLandAPIError) {
            return null;
        }
    }
}
