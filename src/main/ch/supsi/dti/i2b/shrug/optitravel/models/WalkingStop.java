package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

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
}
