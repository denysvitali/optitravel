package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.enums.FeatureStatus;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.annotation.JsonWrapper;

import java.util.ArrayList;

@JsonObject(asExtraForUnknownProperties = false)
public class Route extends ch.supsi.dti.i2b.shrug.optitravel.models.Route {
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

    public Route(){ }

    @JsonCreator
    public static Route fromId(String route_onestop_id){
        Route r = new Route(route_onestop_id);
        return r;
    }

    public Route(String route_onestop_id){
        this.route_onestop_id = route_onestop_id;
    }

    public String getName() {

        if(name == null){
            TransitLandAPIWrapper tl = new TransitLandAPIWrapper();
            try {
                Route route = tl.getRoute(route_onestop_id);
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
            }
        }

        return name;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public String getUID() {
        return getId();
    }

    public String getId() {
        return route_onestop_id;
    }

    @JsonWrapper
    public void setRouteId(@JsonProperty("route_onestop_id") String id){
        this.route_onestop_id = id;
    }

}
