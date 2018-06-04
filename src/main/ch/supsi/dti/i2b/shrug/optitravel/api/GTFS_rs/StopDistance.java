package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;

public class StopDistance {
	private Stop stop;
	private double distance;

	public StopDistance(){

	}

	public double getDistance() {
		return distance;
	}

	public Stop getStop() {
		return stop;
	}
}
