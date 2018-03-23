package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

public class Route {
    public String uid;
    public String agency_id;
    public String short_name;
    public String long_name;
    public String description;
    public int type;

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
}
