package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import com.jsoniter.any.Any;

public class Geometry {
    private String type;
    private Any coordinates;

    public Geometry(){

    }

    public GPSCoordinates getCoordinates() throws TransitLandAPIError {
        if(!type.equals("Point")) {
            throw new TransitLandAPIError("This Geometry is not a Point!");
        }
        return new GPSCoordinates(coordinates);
    }

    public LineString getLineString() throws TransitLandAPIError {

        if(!type.equals("LineString")) {
            throw new TransitLandAPIError("This Geometry is not a LineString!");
        }

        return new LineString(this.coordinates);
    }


    public Polygon getPolygon() throws TransitLandAPIError {
        if(!type.equals("Polygon")){
            throw new TransitLandAPIError("This Geometry is not a Polygon!");
        }
        return new Polygon(coordinates);
    }
    public String getType() {
        return type;
    }
}
