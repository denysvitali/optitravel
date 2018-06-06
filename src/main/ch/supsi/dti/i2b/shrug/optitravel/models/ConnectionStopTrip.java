package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.util.List;

public class ConnectionStopTrip extends StopTrip {

	private int stop_sequence;
	private StopTime stopTime;

	ConnectionStopTrip(StopTime stopTime, int i){
		stop_sequence = i;
		this.stopTime = stopTime;
	}

	@Override
	public Time getDeparture() {
		return stopTime.getTime();
	}

	@Override
	public Time getArrival() {
		return stopTime.getTime();
	}

	@Override
	public DropOff getDropOff() {
		return null;
	}

	@Override
	public PickUp getPickUp() {
		return null;
	}

	@Override
	public Stop getStop() {
		return stopTime.getStop();
	}

	@Override
	public int getStopSequence() {
		return stop_sequence;
	}
}
