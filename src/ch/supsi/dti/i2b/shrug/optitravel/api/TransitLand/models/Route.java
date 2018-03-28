package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.enums.FeatureStatus;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;

public class Route {
    private String route_onestop_id;
    private String name;
    private Operator operator;

    // Fetched via http://transit.land/api/v1/onestop_id/${route_onestop_id}
    private ArrayList<GPSCoordinates> path;
    private String long_name;
    private String vehicle_type;
    private String color;

    @JsonProperty("stops_served_by_route")
    private ArrayList<Stop> stops_served;

    private FeatureStatus wheelchair_accessible;
    private FeatureStatus bikes_allowed;


    public Route(){

    }

    public String getName() {
        return name;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getId() {
        return route_onestop_id;
    }


}
