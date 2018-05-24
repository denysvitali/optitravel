package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

public class WalkStopTime extends StopTime {
	public WalkStopTime(Location s) {
		super(new Stop() {
			@Override
			public String getName() {
				return "Your Destination";
			}
		}, null);
	}

	public WalkStopTime(Coordinate c){
		this(new Location(c));
	}
}
