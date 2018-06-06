package ch.supsi.dti.i2b.shrug.optitravel.params;

import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;

public class LongTripPreference implements PlanPreference {
	private double PREF_WEIGHT;
	private double distance;
	private double average_moving_speed_kmh = 30;
	private double average_moving_speed =  average_moving_speed_kmh * 3.6; // m/s

	public LongTripPreference(double distance){
		this.distance = distance;
		PREF_WEIGHT = 0.5 * distance;
	}

	@Override
	public double walkable_radius_meters() {
		return 800.0;
	}

	@Override
	public double walk_speed_mps() {
		return 1.2;
	}

	@Override
	public double source_radius() {
		return PlannerParams.SOURCE_RADIUS;
	}

	@Override
	public double destination_radius() {
		return Math.max(500, 0.01 * distance);
	}

	@Override
	public double max_waiting_time() {
		return 50.0;
	}

	@Override
	public double w_walk() {
		return 4 * PREF_WEIGHT;
	}

	@Override
	public double w_waiting() {
		return 0.8 * PREF_WEIGHT;
	}

	@Override
	public double w_fast_change() {
		return 6 * PREF_WEIGHT;
	}

	@Override
	public double w_change() {
		return 1 * PREF_WEIGHT;
	}

	@Override
	public double w_moving() {
		return 0.02 * PREF_WEIGHT;
	}

	@Override
	public double max_total_waiting_time() {
		return max_waiting_time()*3;
	}

	@Override
	public int max_total_changes() {
		return 8;
	}

	@Override
	public double max_total_walkable_distance() {
		return Math.min(distance * 0.1, 3000);
	}
}
