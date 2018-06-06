package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.enums.FeatureStatus;
import ch.supsi.dti.i2b.shrug.optitravel.models.Operator;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.annotation.JsonWrapper;

import java.util.ArrayList;

@JsonObject(asExtraForUnknownProperties = false)
public class Route extends ch.supsi.dti.i2b.shrug.optitravel.models.Route {
    private String onestop_id;
    private String name;
    private String operated_by_name;

    // Fetched via http://transit.land/api/v1/onestop_id/${route_onestop_id}
//    private ArrayList<GPSCoordinates> path;
//    private String long_name;
    private String vehicle_type;
    private String color;
/*
    @JsonProperty("stops_served_by_route")
    private ArrayList<Stop> stops_served;

    private FeatureStatus wheelchair_accessible;
    private FeatureStatus bikes_allowed;
*/
    public Route(){ }

    @JsonCreator
    public static Route fromId(String route_onestop_id){
        Route r = new Route(route_onestop_id);
        return r;
    }

    public Route(String route_onestop_id){
        this.onestop_id = route_onestop_id;
    }

    public String getName() {

        if(name == null){
            TransitLandAPIWrapper tl = new TransitLandAPIWrapper();
            try {
                Route route = tl.getRoute(onestop_id);

                this.name = route.name;
                this.operated_by_name = route.operated_by_name;
                this.color = route.color;
                this.vehicle_type = route.vehicle_type;
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
            }
        }

        return name;
    }

    @Override
    public Operator getOperator() {

        if(operated_by_name == null){
            TransitLandAPIWrapper tl = new TransitLandAPIWrapper();
            try {
                Route route = tl.getRoute(onestop_id);

                this.name = route.name;
                this.operated_by_name = route.operated_by_name;
                this.color = route.color;
                this.vehicle_type = route.vehicle_type;
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
            }
        }
        return new ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Operator(operated_by_name);
    }


    @Override
    public String getUID() {
        return getId();
    }

    public String getId() {
        return onestop_id;
    }

    @JsonWrapper
    public void setRouteId(@JsonProperty("route_onestop_id") String id){
        this.onestop_id = id;
    }

}
