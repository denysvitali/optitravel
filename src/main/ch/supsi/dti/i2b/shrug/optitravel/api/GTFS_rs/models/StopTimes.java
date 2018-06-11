package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import com.jsoniter.annotation.JsonObject;

import java.util.List;

@JsonObject
public class StopTimes {
	private String stop;
	private List<TripTimeStop> time;

	public StopTimes(){

	}

	public List<TripTimeStop> getTime() {
		return time;
	}

	public String getStop() {
		return stop;
	}
}
