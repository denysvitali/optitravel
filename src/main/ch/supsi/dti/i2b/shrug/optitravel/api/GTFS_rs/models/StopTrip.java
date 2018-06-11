package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.models.PickUp;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;

import java.util.Objects;


public class StopTrip extends ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip {
    private Stop stop;
    private String arrival_time;
    private String departure_time;
    private int stop_sequence;
    private DropOff drop_off;
    private PickUp pickup;

    public StopTrip(Stop stop,
					String arrival_time,
					String departure_time,
					int stop_sequence,
					DropOff drop_off,
					PickUp pickup){
    	this.stop = stop;
    	this.arrival_time = arrival_time;
    	this.departure_time = departure_time;
    	this.stop_sequence = stop_sequence;
    	this.drop_off = drop_off;
    	this.pickup = pickup;
	}

	public StopTrip(){}

    public Time getDeparture(){
        return new Time(departure_time);
    }

    public Time getArrival(){
        return new Time(arrival_time);
    }

    public DropOff getDropOff() {
        return drop_off;
    }

    public PickUp getPickUp() {
        return pickup;
    }

    public Stop getStop() {
        return stop;
    }

	public int getStopSequence() {
        return stop_sequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stop, arrival_time, departure_time, stop_sequence,
				drop_off, pickup);
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public void setStop_sequence(int stop_sequence) {
		this.stop_sequence = stop_sequence;
	}

	public void setDrop_off(DropOff drop_off) {
		this.drop_off = drop_off;
	}

	public void setPickup(PickUp pickup) {
		this.pickup = pickup;
	}
}
