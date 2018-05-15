package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

public abstract class Stop extends Location{
    public abstract String getName();
    public abstract void findNeighbours(int/*TODO: Time Class*/ arrivalTime);
}
