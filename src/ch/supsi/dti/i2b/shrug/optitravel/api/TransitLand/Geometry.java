package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import com.jsoniter.any.Any;

import java.util.ArrayList;

public class Geometry {
    private String type;
    private Any coordinates;

    public Geometry(){

    }

    public GPSCoordinates getCoordinates() {
        if(type.equals("Point")){
            return new GPSCoordinates(coordinates);
        }
        return null;
    }

    public ArrayList<GPSCoordinates> getPath(){

        if(type.equals("LineString")){
            ArrayList<GPSCoordinates> coordinates = new ArrayList<>();

            ArrayList<ArrayList<Double>> mCoordinates = this.coordinates.as(ArrayList.class);

            for(ArrayList<Double> d : mCoordinates){
                coordinates.add(new GPSCoordinates(d));
            }
            return coordinates;
        } else {
            return null;
        }
    }
    public String getType() {
        return type;
    }
}
