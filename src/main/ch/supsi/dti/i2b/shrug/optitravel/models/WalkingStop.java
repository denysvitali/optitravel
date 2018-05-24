package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class WalkingStop extends Stop {

	private Coordinate coordinate;

	public WalkingStop(Coordinate c){
		this.coordinate = c;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public List<StopTime> findNeighbors(Time time) throws ExecutionControl.NotImplementedException {
		throw new ExecutionControl.NotImplementedException("Not implemented yet.");
	}
}
