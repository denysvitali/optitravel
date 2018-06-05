package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.models.PickUp;
import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;
import jdk.jshell.spi.ExecutionControl;

public class StopTrip extends ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip {

    private Stop stop;
    private Time departure;
    private Time arrival;
    private DropOff dropOff;
    private PickUp pickUp;
    private int stopSequence;

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public void setDeparture(Time departure) {
        this.departure = departure;
    }

    public void setArrival(Time arrival) {
        this.arrival = arrival;
    }

    public void setDropOff(DropOff dropOff) {
        this.dropOff = dropOff;
    }

    public void setPickUp(PickUp pickUp) {
        this.pickUp = pickUp;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    @Override
    public Time getDeparture() {
        return null;
    }

    @Override
    public Time getArrival() {
        return null;
    }

    @Override
    public DropOff getDropOff() {
        return null;
    }

    @Override
    public PickUp getPickUp() {
        return null;
    }

    @Override
    public Stop getStop() {
        return null;
    }

    @Override
    public int getStopSequence() throws ExecutionControl.NotImplementedException {
        return 0;
    }
}
