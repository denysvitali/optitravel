package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import com.jsoniter.any.Any;

import java.util.ArrayList;
import java.util.List;

public class GPSCoordinates {
    private double latitude;
    private double longitude;

    public GPSCoordinates(){

    }

    public GPSCoordinates(ArrayList<Double> coordinate){
        this.latitude = coordinate.get(1);
        this.longitude = coordinate.get(0);
    }

    public GPSCoordinates(Any any){
        List<Any> a = any.asList();
        this.latitude = a.get(1).toDouble();
        this.longitude = a.get(0).toDouble();
    }

    public GPSCoordinates(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
