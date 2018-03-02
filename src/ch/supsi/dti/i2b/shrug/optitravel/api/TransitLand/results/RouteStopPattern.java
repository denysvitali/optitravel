package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.Geometry;

import java.util.ArrayList;

public class RouteStopPattern {
    private Geometry geometry;
    private String onestop_id;
    private String created_at;
    private String updated_at;
    private int created_or_updated_in_changeset_id;
    private String route_onestop_id;
    private ArrayList<String> stop_pattern;
    private ArrayList<Double> stop_distances;
    private String geometry_source;
    private String color;
    private ArrayList<String> trips;


    public Geometry getGeometry() {
        return geometry;
    }
}
