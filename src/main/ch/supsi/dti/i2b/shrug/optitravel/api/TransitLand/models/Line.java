package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import com.jsoniter.any.Any;

import java.util.ArrayList;

public class Line {
    private ArrayList<GPSCoordinates> points;

    public Line(){

    }

    public Line(Any a){
        this.points = new ArrayList<>();
        for(Any point : a.asList()){
            this.points.add(new GPSCoordinates(point));
        }
    }

    public ArrayList<GPSCoordinates> getPoints() {
        return points;
    }

    public GPSCoordinates getPoint(int index){
        return points.get(index);
    }
}
