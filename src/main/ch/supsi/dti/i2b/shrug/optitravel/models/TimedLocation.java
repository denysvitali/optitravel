package ch.supsi.dti.i2b.shrug.optitravel.models;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;

import java.util.HashMap;

public abstract class TimedLocation extends Location {

	private Location location;
	private Time time;
	private Trip trip;

	TimedLocation(Location location, Time time){
		this.location = location;
		this.time = time;
	}

	public Location getLocation(){
		return location;
	}

	public Time getTime(){
		return time;
	}

	public Trip getTrip() {
		return trip;
	}

	public abstract HashMap<Node<?,?>, Double> getNeighbours();

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	@Override
	public Coordinate getCoordinate() {
		return location.getCoordinate();
	}
}
