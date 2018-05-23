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
