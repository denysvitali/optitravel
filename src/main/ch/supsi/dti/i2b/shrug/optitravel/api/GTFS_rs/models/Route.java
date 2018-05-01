package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.Operator;

public class Route extends ch.supsi.dti.i2b.shrug.optitravel.models.Route {
    public String uid;
    public String agency_id;
    public String short_name;
    public String long_name;
    public String description;
    public int type;

    public Route(){}
    public Route(String uid){
        this.uid = uid;
    }

    public String getUID() {
        return uid;
    }

    public String getAgencyUID() {
        return agency_id;
    }

    public String getShortName() {
        return short_name;
    }

    public String getLongName() {
        return long_name;
    }

    public String getDescription() {
        return description;
    }

    public RouteType getRouteType() {
        return RouteType.getRoute(type);
    }

    @Override
    public String getName() {
        return getShortName();
    }

    @Override
    public Operator getOperator() {
        return new Agency(getAgencyUID());
    }
}
