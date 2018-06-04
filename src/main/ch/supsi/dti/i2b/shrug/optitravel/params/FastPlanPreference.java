package ch.supsi.dti.i2b.shrug.optitravel.params;

import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;

public class FastPlanPreference implements PlanPreference {
	private double PREF_WEIGHT;
	private double distance;
	private double average_moving_speed_kmh = 20;
	private double average_moving_speed; // m/s

	public FastPlanPreference(double distance){
		this.distance = distance;
		average_moving_speed = average_moving_speed_kmh * 3.6;
		PREF_WEIGHT = 0.5 * distance;
	}

	@Override
	public double walkable_radius_meters() {
		return 1500;
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
		return 350;
	}

	@Override
	public double max_waiting_time() {
		return 25.0;
	}

	@Override
	public double w_walk() {
		return 1.5 * PREF_WEIGHT;
	}

	@Override
	public double w_waiting() {
		return PREF_WEIGHT;
	}

	@Override
	public double w_fast_change() {
		return 6 * PREF_WEIGHT;
	}

	@Override
	public double w_change() {
		return 1.4 * PREF_WEIGHT;
	}

	@Override
	public double w_moving() {
		return 0.3 * PREF_WEIGHT;
	}

	@Override
	public double max_total_waiting_time() {
		return 20.0;
	}

	@Override
	public int max_total_changes() {
		return 5;
	}
}
