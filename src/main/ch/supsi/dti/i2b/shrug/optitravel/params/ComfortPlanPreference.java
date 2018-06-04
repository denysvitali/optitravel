package ch.supsi.dti.i2b.shrug.optitravel.params;

import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;

public class ComfortPlanPreference implements PlanPreference {
	private static double PREF_WEIGHT = 100;

	@Override
	public double walkable_radius_meters() {
		return 800;
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
		return 25.0;
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
		return 20 * PREF_WEIGHT;
	}

	@Override
	public double w_change() {
		return 8 * PREF_WEIGHT;
	}

	@Override
	public double w_moving() {
		return 0.1 * PREF_WEIGHT;
	}

	@Override
	public double max_total_waiting_time() {
		return max_waiting_time();
	}

	@Override
	public int max_total_changes() {
		return 6;
	}
}
