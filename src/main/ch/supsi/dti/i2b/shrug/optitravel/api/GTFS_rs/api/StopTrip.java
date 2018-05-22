package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;

import ch.supsi.dti.i2b.shrug.optitravel.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.models.PickUp;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;


public class StopTrip extends ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip {
    public Stop stop;
    public String arrival_time;
    public String departure_time;
    public int stop_sequence;
    public DropOff drop_off;
    public PickUp pickup;

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
