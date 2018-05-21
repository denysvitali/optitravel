package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.StopTrip;

import java.util.List;

public class Trip extends ch.supsi.dti.i2b.shrug.optitravel.models.Trip {
    public String uid;
    public String route_id;
    public String service_id;
    public String headsign;
    public String short_name;
    public int direction_id;
    public List<StopTrip> stop_sequence;

    public String getUID() {
        return this.uid;
    }
    public List<StopTrip> getStopTrip() {
        return stop_sequence;
    }
    public Route getRoute() {
        return new Route(route_id);
    }
}
