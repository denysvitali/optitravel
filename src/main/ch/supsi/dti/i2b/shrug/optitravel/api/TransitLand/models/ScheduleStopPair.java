package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.Date;
import ch.supsi.dti.i2b.shrug.optitravel.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.models.PickUp;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;
import com.jsoniter.any.Any;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleStopPair extends ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip {

    private String feed_onestop_id;
    private String feed_version_sha;
    private String route_onestop_id;
    private String route_stop_pattern_onestop_id;
    private String operator_onestop_id;
    private String origin_onestop_id;
    private String origin_timezone;
    private String origin_arrival_time;
    private String origin_departure_time;
    private String origin_timepoint_source;
    private Any origin_dist_traveled;
    private String destination_onestop_id;
    private String destination_timezone;
    private String destination_arrival_time;
    private String destination_departure_time;
    private String destination_timepoint_source;
    private Any destination_dist_traveled;
    private String window_start;
    private String window_end;
    private String trip;
    private String trip_headsign;
    private String trip_short_name;
    private String block_id;
    private String service_start_date;
    private String service_end_date;
    private boolean[] service_days_of_week = new boolean[7];
    private ArrayList<Any> service_added_dates = new ArrayList<>();
    private ArrayList<Any> service_except_dates = new ArrayList<>();
    private Any wheelchair_accessible;
    private Any bikes_allowed;
    private String drop_off_type;
    private String pickup_type;
    private String frequency_type;
    private Any frequency_headway_seconds;
    private String frequency_start_time;
    private String frequency_end_time;

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

    public Time getOrigin_arrival_time() {
        return new Time(origin_arrival_time);
    }

    public Double getOrigin_dist_traveled() {
        return (origin_dist_traveled == null ? 0.0 :
				origin_dist_traveled.as(double.class));
    }

    public String getDestination_onestop_id() {
        return destination_onestop_id;
    }

    public String getDestination_timezone() {
        return destination_timezone;
    }

    public Time getDestination_arrival_time() {
        return new Time(destination_arrival_time);
    }

    public Time getDestination_departure_time() {
        return new Time(destination_departure_time);
    }

    public Double getDestination_dist_traveled() {
        return (destination_dist_traveled == null ? 0.0 :
				destination_dist_traveled.as(double.class));
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

    public List<Date> getService_added_dates() {
        return service_added_dates.stream().map((a)->
				new Date(a.as(String.class))).collect(Collectors.toList());
    }

    public List<Date> getService_except_dates() {
        return service_except_dates.stream().map((a)->
				new Date(a.as(String.class))).collect(Collectors.toList());
    }

	@Override
	public Time getDeparture() {
		return new Time(origin_departure_time);
	}

	@Override
	public Time getArrival() {
		return new Time(origin_arrival_time);
	}

	@Override
	public DropOff getDropOff() {
		switch(pickup_type){
			case "unavailable":
				return DropOff.NotAvailable;
			case "ask_driver":
				return DropOff.MustCoordinateWithDriver;
			case "ask_agency":
				return DropOff.MustArrangeWithAgency;
			default:
				return DropOff.RegularlyScheduled;
		}
	}

	@Override
	public PickUp getPickUp() {
    	switch(pickup_type){
			case "unavailable":
				return PickUp.NotAvailable;
			case "ask_driver":
				return PickUp.MustCoordinateWithDriver;
			case "ask_agency":
				return PickUp.MustArrangeWithAgency;
			default:
				return PickUp.RegularlyScheduled;
		}
	}

	@Override
	public ch.supsi.dti.i2b.shrug.optitravel.models.Stop getStop() {
		return new ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop(
				origin_onestop_id
		);
	}

	@Override
	public int getStopSequence() throws ExecutionControl.NotImplementedException {
    	// TODO: @Pura Implement
		throw new ExecutionControl.NotImplementedException("Not yet implemented!");
	}
}
