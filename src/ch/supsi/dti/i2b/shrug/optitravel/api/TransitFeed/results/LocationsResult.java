package ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed.results;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed.Location;

import java.util.ArrayList;

public class LocationsResult {
    private String input;
    private ArrayList<Location> locations = new ArrayList<>();

    public LocationsResult(){
        locations = new ArrayList<>();
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
}
