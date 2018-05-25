package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.Time;

public class TripTime {
	public String trip;
	public String time;

	public TripTime(){
	}

	public Time getTime() {
		return new Time(time);
	}

	public String getTrip() {
		return trip;
	}
}
