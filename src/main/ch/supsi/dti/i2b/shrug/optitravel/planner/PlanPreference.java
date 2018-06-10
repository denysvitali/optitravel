package ch.supsi.dti.i2b.shrug.optitravel.planner;

public interface PlanPreference {
	double walkable_radius_meters();
	double walk_speed_mps();
	double source_radius();
	double destination_radius();
	double max_waiting_time();

	// Weights
	double w_walk();
	double w_waiting();
	double w_fast_change();
	double w_change();
	double w_moving();

	double max_total_waiting_time();
	int max_total_changes();

	double max_total_walkable_distance();
}
