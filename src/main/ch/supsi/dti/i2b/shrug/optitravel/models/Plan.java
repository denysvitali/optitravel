package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Plan {
    private List<Trip> trips;
    private Time start_time;
    private Time end_time;

    private Coordinate start_location;
    private Coordinate end_location;

    public Plan(List<Trip> trips,
				Time start_time,
				Time end_time,
				Coordinate start_location,
				Coordinate end_location){
    	this.trips = trips;
    	this.start_time = start_time;
    	this.end_time = end_time;
    	this.start_location = start_location;
    	this.end_location = end_location;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public Coordinate getEndLocation() {
		return end_location;
	}

	public Coordinate getStartLocation() {
		return start_location;
	}

	public Time getStartTime() {
		return start_time;
	}

	public Time getEndTime() {
		return end_time;
	}
}
