package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.StopTime;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonObject(asExtraForUnknownProperties = false)
public class Stop extends ch.supsi.dti.i2b.shrug.optitravel.models.Stop {
    private Geometry geometry;
    private String onestop_id;
    private String name;
    private ArrayList<Operator> operators_serving_stop;
    private ArrayList<Route> routes_serving_stop;

//    private ArrayList<RouteStopPattern> contained_in;

    @JsonCreator
    public Stop(){

    }

    public Stop(String uid){
        onestop_id = uid;
    }

    @JsonCreator
    public static Stop fromId(String onestop_id){
        Stop s = new Stop(onestop_id);
        return s;
    }

    public String getId() {
        return onestop_id;
    }

    public String getName() {
        return name;
    }

    @Override
    public Coordinate getCoordinate() {
        GPSCoordinates coordinates = getCoordinates();
        if(coordinates == null){
            return null;
        }
        return new Coordinate(coordinates.getLatitude(), coordinates.getLongitude());
    }

    @Override
    public String getUid() {
        return getId();
    }

    @Override
    public ch.supsi.dti.i2b.shrug.optitravel.models.Stop getParentStop() {
        return null;
    }

    public List<StopTime> findNeighbors(Time time) {
        return null;
    }

    public ArrayList<Operator> getOperators() {
        return operators_serving_stop;
    }

    public ArrayList<Route> getRoutes() {
        return routes_serving_stop;
    }

    public GPSCoordinates getCoordinates() {
        try {
            return geometry.getCoordinates();
        } catch (TransitLandAPIError transitLandAPIError) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("\"%s\" - %s (%s)",
                getName(),
                getUid(),
                getCoordinate());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return Objects.equals(this.onestop_id, stop.onestop_id);
    }

}
