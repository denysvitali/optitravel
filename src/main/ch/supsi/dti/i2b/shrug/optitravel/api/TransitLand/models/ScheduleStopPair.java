package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import java.util.ArrayList;

public class ScheduleStopPair {

    private String feed_onestop_id;
    //private String feed_version_sha;
    private String route_onestop_id;
    private String route_stop_pattern_onestop_id;
    private String operator_onestop_id;
    private String origin_onestop_id;
    private String origin_timezone;
    private String origin_arrival_time;
    //origin timepoint source
    private Double origin_dist_traveled;
    private String destination_onestop_id;
    private String destination_timezone;
    private String destination_arrival_time;
    private String destination_departure_time;
    // destination timepoint source
    private Double destination_dist_traveled;
    private String trip;
    private String trip_headsign;
    private String trip_short_name;
    private String block_id;
    private String service_start_date;
    private String service_end_date;
    private boolean[] service_days_of_week = new boolean[7];
    private ArrayList<String> service_added_dates = new ArrayList<>();
    private ArrayList<String> service_except_dates = new ArrayList<>();

    public String getFeed_onestop_id() {
        return feed_onestop_id;
    }

    public String getRoute_onestop_id() {
        return route_onestop_id;
    }

    public String getRoute_stop_pattern_onestop_id() {
        return route_stop_pattern_onestop_id;
    }

    public String getOperator_onestop_id() {
        return operator_onestop_id;
    }

    public String getOrigin_onestop_id() {
        return origin_onestop_id;
    }

    public String getOrigin_timezone() {
        return origin_timezone;
    }

    public String getOrigin_arrival_time() {
        return origin_arrival_time;
    }

    public Double getOrigin_dist_traveled() {
        return origin_dist_traveled;
    }

    public String getDestination_onestop_id() {
        return destination_onestop_id;
    }

    public String getDestination_timezone() {
        return destination_timezone;
    }

    public String getDestination_arrival_time() {
        return destination_arrival_time;
    }

    public String getDestination_departure_time() {
        return destination_departure_time;
    }

    public Double getDestination_dist_traveled() {
        return destination_dist_traveled;
    }

    public String getTrip() {
        return trip;
    }

    public String getTrip_headsign() {
        return trip_headsign;
    }

    public String getTrip_short_name() {
        return trip_short_name;
    }

    public String getBlock_id() {
        return block_id;
    }

    public String getService_start_date() {
        return service_start_date;
    }

    public String getService_end_date() {
        return service_end_date;
    }

    public boolean[] getService_days_of_week() {
        return service_days_of_week;
    }

    public ArrayList<String> getService_added_dates() {
        return service_added_dates;
    }

    public ArrayList<String> getService_except_dates() {
        return service_except_dates;
    }
}
