package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;
import jdk.jshell.spi.ExecutionControl;

import java.util.HashMap;
import java.util.List;
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

	@Override
	public HashMap<Node<?,?>, Double> getNeighbours() {
		// TODO: Implement default
		/*try {
			List<StopTime> neighbors = stop.findNeighbors(time);
		} catch (ExecutionControl.NotImplementedException e) {
			e.printStackTrace();
		}*/
		return new HashMap<>();
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
	public int hashCode() {
		return Objects.hash(stop, time);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		StopTime stopTime = (StopTime) o;
		return Objects.equals(stop, stopTime.stop) &&
				Objects.equals(time, stopTime.time);
	}

	@Override
	public Coordinate getCoordinate() {
		return stop.getCoordinate();
	}

	public void setTrip(Trip t) {
		trip = t;
	}
}
