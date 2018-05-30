package ch.supsi.dti.i2b.shrug.optitravel.params;

import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;

public class LongTripPreference implements PlanPreference {
	private double PREF_WEIGHT;
	private double distance;
	private double average_moving_speed_kmh = 15.0;
	private double average_moving_speed; // m/s

	public LongTripPreference(double distance){
		this.distance = distance;
		average_moving_speed = average_moving_speed_kmh * 3.6;
		PREF_WEIGHT = 1 * distance/average_moving_speed;
	}

	@Override
	public double walkable_radius_meters() {
		return 50;
	}

	@Override
	public double walk_speed_mps() {
		return 1;
	}

	@Override
	public double source_radius() {
		return PlannerParams.SOURCE_RADIUS;
	}

	@Override
	public double destination_radius() {
		return PlannerParams.DESTINATION_RADIUS;
	}

	@Override
	public double max_waiting_time() {
		return 45.0;
	}

	@Override
	public double w_walk() {
		return 3 * PREF_WEIGHT;
	}

	@Override
	public double w_waiting() {
		return 1.4 * PREF_WEIGHT;
	}

	@Override
	public double w_fast_change() {
		return 10 * PREF_WEIGHT;
	}

	@Override
	public double w_change() {
		return 5 * PREF_WEIGHT;
	}

	@Override
	public double w_moving() {
		return PREF_WEIGHT;
	}
}
