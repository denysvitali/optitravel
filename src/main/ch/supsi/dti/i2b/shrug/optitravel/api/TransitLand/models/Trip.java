package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trip extends ch.supsi.dti.i2b.shrug.optitravel.models.Trip {

    private List<StopTrip> stop_sequence = new ArrayList<>();
    private Route route;
    private String route_stop_pattern_id;
    private String trip_id;
    private String trip_headsign;


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

    public void add_stop_sequence(StopTrip stopTrip) {
        stop_sequence.add(stopTrip);
    }


    public void setTrip_headsign(String trip_headsign) {
        this.trip_headsign = trip_headsign;
    }

    @Override
    public List<StopTrip> getStopTrip() {
        return stop_sequence;
    }

    @Override
    public Route getRoute() {
        return route;
    }

    @Override
    public void setStopTrip(List<StopTrip> stopTrip) {
        this.stop_sequence = stopTrip;
    }

    @Override
    public String getHeadSign() {

        return trip_headsign;
    }

    @Override
    public String toString() {
        return getRoute_stop_pattern_id() + " Trip_id: " + getTrip_id();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(this.route_stop_pattern_id, trip.route_stop_pattern_id) &&
                Objects.equals(this.trip_id, trip.trip_id);
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
