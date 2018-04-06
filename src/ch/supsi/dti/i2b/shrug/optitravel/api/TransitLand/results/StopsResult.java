package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;

import java.util.ArrayList;

public class StopsResult {
    private ArrayList<Stop> stops;
    private Meta meta;

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public Meta getMeta() {
        return meta;
    }
}
