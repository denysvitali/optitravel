package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public abstract class Stop extends Location {
    public abstract String getName();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public abstract Coordinate getCoordinate();

	public abstract List<StopTime> findNeighbors(Time time) throws ExecutionControl.NotImplementedException;
}
