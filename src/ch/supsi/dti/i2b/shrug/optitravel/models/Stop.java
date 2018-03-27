package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

public abstract class Stop {
    public abstract String getName();
    public abstract Coordinate getCoordinate();
}
