package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;

import java.util.ArrayList;

public class RouteStopPatternsResult {
    private ArrayList<RouteStopPattern> route_stop_patterns;

    private Meta meta;

    public ArrayList<RouteStopPattern> getRouteStopPatterns() {
        return route_stop_patterns;
    }

    public Meta getMeta(){
        return meta;
    }
}
