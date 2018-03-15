package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import com.jsoniter.any.Any;

import java.util.ArrayList;

public class LineString{

    private ArrayList<GPSCoordinates> coordinates;

    public LineString(Any a){
        this.coordinates = new ArrayList<>();
        for(Any point : a.asList()){
            this.coordinates.add(new GPSCoordinates(point));
        }
    }

    public int size(){
        return this.coordinates.size();
    }

    public GPSCoordinates get(int index){
        return coordinates.get(index);
    }
}
