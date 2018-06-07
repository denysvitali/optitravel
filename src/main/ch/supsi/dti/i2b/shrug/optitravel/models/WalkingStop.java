package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class WalkingStop extends Stop {

	private Coordinate coordinate;
	private static final long serialVersionUID = 8422745393664760809L;

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
	public String getUid() {
		return "ws-" + coordinate.toString().replace(".", "-");
	}

	@Override
	public Stop getParentStop() {
		return null;
	}

	@Override
	public String toString() {
		return getUid();
	}

	public List<StopTime> findNeighbors(Time time) throws ExecutionControl.NotImplementedException {
		throw new ExecutionControl.NotImplementedException("Not implemented yet.");
	}
}
