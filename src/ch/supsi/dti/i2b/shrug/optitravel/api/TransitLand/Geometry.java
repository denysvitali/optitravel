package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import java.util.ArrayList;

public class Geometry {
    private String type;
    private ArrayList<Double> coordinates;

    public Geometry(){

    }

    public GPSCoordinates getCoordinates() {
        return new GPSCoordinates(coordinates);
    }

    public String getType() {
        return type;
    }
}
