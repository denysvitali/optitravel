package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;

import ch.supsi.dti.i2b.shrug.optitravel.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.models.PickUp;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;


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
}
