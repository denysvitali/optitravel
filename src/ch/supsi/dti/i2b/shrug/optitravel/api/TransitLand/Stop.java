package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import java.util.ArrayList;

public class Stop {
    private Geometry geometry;
    private String onestop_id;
    private String name;
    private ArrayList<Operator> operators_serving_stop;
    private ArrayList<Route> routes_serving_stop;

    public Stop(){

    }

    public String getName() {
        return name;
    }

    public ArrayList<Operator> getOperators() {
        return operators_serving_stop;
    }

    public ArrayList<Route> getRoutes() {
        return routes_serving_stop;
    }

    public GPSCoordinates getCoordinates() {
        return geometry.getCoordinates();
    }
}
