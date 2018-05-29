package ch.supsi.dti.i2b.shrug.optitravel.params;

import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;

public class LongTripPreference implements PlanPreference {
	private double PREF_WEIGHT = 100;
	private double distance = 1.0;

	public LongTripPreference(double distance){
		this.distance = distance;
		PREF_WEIGHT = 0.4 * distance;
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
		return 60.0;
	}

	@Override
	public double w_walk() {
		return 80 * PREF_WEIGHT;
	}

	@Override
	public double w_waiting() {
		return 8 * PREF_WEIGHT;
	}

	@Override
	public double w_fast_change() {
		return 5000 * PREF_WEIGHT;
	}

	@Override
	public double w_change() {
		return 800 * PREF_WEIGHT;
	}

	@Override
	public double w_moving() {
		return 3 * PREF_WEIGHT;
	}
}
