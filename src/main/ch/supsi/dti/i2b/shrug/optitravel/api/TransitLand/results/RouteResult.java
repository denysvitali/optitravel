package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Route;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;

import java.util.ArrayList;

public class RouteResult {

    private ArrayList<Route> routes;
    private Meta meta;

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public Meta getMeta() {
        return meta;
    }
}
