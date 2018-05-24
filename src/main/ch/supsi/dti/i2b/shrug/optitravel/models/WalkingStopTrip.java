package ch.supsi.dti.i2b.shrug.optitravel.models;

import java.util.Objects;

public class WalkingStopTrip extends StopTrip {

	private Stop stop;
	private String arrival_time;
	private String departure_time;
	private int stop_sequence;
	private DropOff dropoff;
	private PickUp pickup;

	public WalkingStopTrip(Stop s, String at, String dt, int ss, DropOff dro,
						   PickUp piu) {
		stop = s;
		arrival_time = at;
		departure_time = dt;
		stop_sequence = ss;
		dropoff = dro;
		pickup = piu;
	}


	@Override
	public Time getDeparture() {
		return new Time(departure_time);
	}

	@Override
	public Time getArrival() {
		return new Time(arrival_time);
	}

	@Override
	public DropOff getDropOff() {
		return dropoff;
	}

	@Override
	public PickUp getPickUp() {
		return pickup;
	}

	@Override
	public Stop getStop() {
		return stop;
	}

	@Override
	public int getStopSequence() {
		return stop_sequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stop, arrival_time, departure_time, stop_sequence,
				dropoff, pickup);
	}
}
