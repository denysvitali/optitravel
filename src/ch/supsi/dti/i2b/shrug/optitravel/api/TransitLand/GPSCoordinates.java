package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import java.util.ArrayList;

public class GPSCoordinates {
    private double latitude;
    private double longitude;

    public GPSCoordinates(ArrayList<Double> coordinate){
        this.latitude = coordinate.get(1);
        this.longitude = coordinate.get(0);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
