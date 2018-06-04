package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

public class WalkStopTime extends StopTime {

	public WalkStopTime(Coordinate c){
		super(new WalkingStop(c), null);
	}

	public WalkStopTime(Coordinate c, Time t){
		super(new WalkingStop(c), t);
	}
}
