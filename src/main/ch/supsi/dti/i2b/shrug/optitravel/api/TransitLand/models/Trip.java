package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip;

import java.util.List;

public class Trip extends ch.supsi.dti.i2b.shrug.optitravel.models.Trip {

    private List<StopTrip> stop_sequence;
    private Route route;
    private String route_stop_pattern_id;
    private String trip_id;


    public String getRoute_stop_pattern_id() {
        return route_stop_pattern_id;
    }

    public void setRoute_stop_pattern_id(String route_stop_pattern_id) {
        this.route_stop_pattern_id = route_stop_pattern_id;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public void add_stop_sequence(ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.StopTrip stopTrip) {
        stop_sequence.add(stopTrip);
    }

    @Override
    public List<StopTrip> getStopTrip() {
        return stop_sequence;
    }

    @Override
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
