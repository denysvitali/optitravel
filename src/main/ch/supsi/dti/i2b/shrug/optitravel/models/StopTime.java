package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;

import java.util.Objects;

public class StopTime extends TimedLocation {
	private Stop stop;
	private Time time;

	public StopTime(Stop s, Time t){
		super(s,t);
		stop = s;
		time = t;
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
		return getStop() + " @ " + getTime();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StopTime stopTime = (StopTime) o;
		return Objects.equals(stop, stopTime.stop) &&
				Objects.equals(time, stopTime.time);
	}

	@Override
	public Coordinate getCoordinate() {
		return stop.getCoordinate();
	}

	@Override
	public int hashCode() {
		return Objects.hash(stop, time);
	}
}
