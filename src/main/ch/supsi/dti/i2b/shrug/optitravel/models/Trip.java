package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.StopTrip;

import java.util.List;

public abstract class Trip {
	public abstract List<StopTrip> getStopTrip();
	public abstract Route getRoute();
}
