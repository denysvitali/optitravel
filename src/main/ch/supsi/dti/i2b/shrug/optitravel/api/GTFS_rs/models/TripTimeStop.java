package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.Time;

public class TripTimeStop {
	public String trip;
	public String time;
	public String next_stop;

	public TripTimeStop(){
	}

	public Time getTime() {
		return new Time(time);
	}

	public String getTrip() {
		return trip;
	}

	public String getNextStop() {
		return next_stop;
	}
}
