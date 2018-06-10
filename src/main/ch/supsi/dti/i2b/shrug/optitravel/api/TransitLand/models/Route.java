package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.enums.FeatureStatus;
import ch.supsi.dti.i2b.shrug.optitravel.models.Operator;
import ch.supsi.dti.i2b.shrug.optitravel.models.RouteType;
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
    @JsonProperty("color")
    private String color;

    private boolean additionalInfosFetched = false;
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

    private void fetchAdditionalInfos(){
    	if(additionalInfosFetched){
    		return;
		}

		TransitLandAPIWrapper tl = new TransitLandAPIWrapper();
		try {
			Route route = tl.getRoute(onestop_id);
			this.name = route.name;
			this.operated_by_name = route.operated_by_name;
			this.color = route.color;
			this.vehicle_type = route.vehicle_type;
			additionalInfosFetched = true;
		} catch (TransitLandAPIError transitLandAPIError) {
			transitLandAPIError.printStackTrace();
		}
	}

    public String getName() {
        if(name == null){
			fetchAdditionalInfos();
        }

        return name;
    }

    @Override
    public Operator getOperator() {
        if(operated_by_name == null){
			fetchAdditionalInfos();
        }
        return new ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Operator(operated_by_name);
    }


    @Override
    public String getUID() {
        return getId();
    }

    @Override
    public RouteType getType() {
    	if(vehicle_type == null){
			fetchAdditionalInfos();
		}

		switch (vehicle_type){
			case "tram":
				return RouteType.TRAM_SERVICE;
			case "metro":
				return RouteType.METRO_SERVICE;
			case "rail":
				return RouteType.RAILWAY_SERVICE;
			case "bus":
				return RouteType.BUS_SERVICE;
			case "ferry":
				return RouteType.FERRY_SERVICE;
			case "cablecar":
				return RouteType.CABLE_CAR;
			case "gondola":
				return RouteType.CABLE_DRAWN_BOAT_SERVICE;
			case "funicular":
				return RouteType.FUNICULAR_SERVICE;
			case "railway_service":
				return RouteType.RAILWAY_SERVICE;
			case "high_speed_rail_service":
				return RouteType.HIGH_SPEED_RAIL_SERVICE;

			default:
				return null;
		}
    }

	@Override
	public String getColor() {
    	if(color == null){
			fetchAdditionalInfos();
		}

		return color;
	}

	@Override
	public String getTextColor() {
		return null;
	}

	@Override
	public void setColor(String color) {
    	this.color = color;
	}

	public String getId() {
        return onestop_id;
    }

    @JsonWrapper
    public void setRouteId(@JsonProperty("route_onestop_id") String id){
        this.onestop_id = id;
    }

}
