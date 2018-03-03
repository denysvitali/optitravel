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

    public String getId() {
        return onestop_id;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public int getCreatedOrUpdatedInChangesetId() {
        return created_or_updated_in_changeset_id;
    }

    public String getRouteOnestopId() {
        return route_onestop_id;
    }

    public ArrayList<String> getStopPattern() {
        return stop_pattern;
    }

    public ArrayList<Double> getStopDistances() {
        return stop_distances;
    }

    public String getGeometrySource() {
        return geometry_source;
    }

    public String getColor() {
        return color;
    }

    public ArrayList<String> getTrips() {
        return trips;
    }
}
