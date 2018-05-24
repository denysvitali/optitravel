package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;

import java.util.Objects;

public class StopTime extends TimedLocation {
	private Stop stop;
	private Time time;
	private Trip trip;

	public StopTime(Stop s, Time t){
		super(s,t);
		stop = s;
		time = t;
	}

	public StopTime(Stop s, Time t, Trip tr){
		super(s,t);
		stop = s;
		time = t;
		trip = tr;
	}

	public Trip getTrip() {
		return trip;
	}

	public Stop getStop() {
		return stop;
	}

	@Override
	public Location getLocation() {
		return stop;
	}

	public Time getTime() {
		return time;
	}

	@Override
	public String toString() {
		return getStop() + " @ " + getTime() + " (via " + (trip == null ? "N/A" : trip) + ")";
	}

	@Override
	public Coordinate getCoordinate() {
		return stop.getCoordinate();
	}

	public void setTrip(Trip t) {
		trip = t;
	}
}
