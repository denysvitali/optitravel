package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import java.util.List;

public class StopTimes {
	private String stop;
	private List<TripTime> time;

	public StopTimes(){

	}

	public List<TripTime> getTime() {
		return time;
	}

	public String getStop() {
		return stop;
	}
}
