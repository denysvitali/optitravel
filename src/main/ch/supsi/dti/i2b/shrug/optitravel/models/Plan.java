package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Plan {
    private LinkedList<Trip> trips;
    private LocalDateTime start_time;
    private LocalDateTime end_time;

    private GPSCoordinates start_location;
    private GPSCoordinates end_location;

}
