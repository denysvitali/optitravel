package ch.supsi.dti.i2b.shrug.optitravel.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

import java.util.List;

public class WaitingTrip extends Trip {
	private static final long serialVersionUID = 893576653673057461L;
	private Location wait_at;
	private double wait_time;

	public WaitingTrip(Location wait_at, double wait_time) {
		this.wait_at = wait_at;
		this.wait_time = wait_time;
	}

	public double getWaitTime() {
		return wait_time;
	}

	public Location getWaitLocation() {
		return wait_at;
	}

	@Override
	public List<StopTrip> getStopTrip() {
		return null;
	}

	@Override
	public Route getRoute() {
		return new WaitingRoute();
	}

	@Override
	public void setStopTrip(List<StopTrip> stopTrip) {

	}

	@Override
	public String getHeadSign() {
		return null;
	}

	@Override
	public void setRoute(Route route) {

	}
}
