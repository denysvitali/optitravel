package ch.supsi.dti.i2b.shrug.optitravel.api.BlaBlaCar.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.Route;
import ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip;
import ch.supsi.dti.i2b.shrug.optitravel.models.Trip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BlaBlaTrip extends Trip {

  //  private ArrayList<String> links;
    private String departure_date;
    private BlaBlaStop departure_place;
    private BlaBlaStop arrival_place;

    public String getDeparture_date() {
        return departure_date;
    }

    public BlaBlaStop getDeparture_place() {
        return departure_place;
    }

    public BlaBlaStop getArrival_place() {
        return arrival_place;
    }

    @Override
    public List<StopTrip> getStopTrip() {
        return null;
    }

    @Override
    public Route getRoute() {
        return null;
    }
}
