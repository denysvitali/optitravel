package ch.supsi.dti.i2b.shrug.optitravel.models;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

public class TimedLocation extends Location {

	private Location location;
	private Time time;

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

	@Override
	public Coordinate getCoordinate() {
		return location.getCoordinate();
	}
}
