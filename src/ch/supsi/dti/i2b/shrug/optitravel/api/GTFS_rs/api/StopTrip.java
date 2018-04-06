package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.PickUp;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class StopTrip {
    public Stop stop;
    public String arrival_time;
    public String departure_time;
    public int stop_sequence;
    public DropOff drop_off;
    public PickUp pickup;

    public LocalTime getDeparture(){
        return LocalTime.parse(departure_time);
    }

    public LocalTime getArrival(){
        return LocalTime.parse(arrival_time);
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
}
