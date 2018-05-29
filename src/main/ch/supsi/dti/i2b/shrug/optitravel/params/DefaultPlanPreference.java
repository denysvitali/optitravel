package ch.supsi.dti.i2b.shrug.optitravel.params;

import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;

public class DefaultPlanPreference implements PlanPreference {
	@Override
	public double walkable_radius_meters() {
		return PlannerParams.WALKABLE_RADIUS_METERS;
	}

	@Override
	public double walk_speed_mps() {
		return PlannerParams.WALK_SPEED_MPS;
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
		return PlannerParams.MAX_WAITING_TIME;
	}

	@Override
	public double w_walk() {
		return PlannerParams.W_WALK;
	}

	@Override
	public double w_waiting() {
		return PlannerParams.W_WAITING;
	}

	@Override
	public double w_fast_change() {
		return PlannerParams.W_FAST_CHANGE;
	}

	@Override
	public double w_change() {
		return PlannerParams.W_CHANGE;
	}

	@Override
	public double w_moving() {
		return PlannerParams.W_MOVING;
	}
}
