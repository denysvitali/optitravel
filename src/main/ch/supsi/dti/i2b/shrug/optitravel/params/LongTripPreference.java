package ch.supsi.dti.i2b.shrug.optitravel.params;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;

public class LongTripPreference implements PlanPreference {
	private double PREF_WEIGHT;
	private double distance;
	private double average_moving_speed_kmh = 6.0;
	private double average_moving_speed; // m/s

	public LongTripPreference(double distance){
		this.distance = distance;
		average_moving_speed = average_moving_speed_kmh * 3.6;
		PREF_WEIGHT = 1 * distance/average_moving_speed;
	}

	@Override
	public double walkable_radius_meters() {
		return 1200;
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
		return 800;
	}

	@Override
	public double max_waiting_time() {
		return 20.0;
	}

	@Override
	public double w_walk() {
		return 1.2 * PREF_WEIGHT;
	}

	@Override
	public double w_waiting() {
		return 0.7 * PREF_WEIGHT;
	}

	@Override
	public double w_fast_change() {
		return 5 * PREF_WEIGHT;
	}

	@Override
	public double w_change() {
		return 0.5 * PREF_WEIGHT;
	}

	@Override
	public double w_moving() {
		return 0.03 * PREF_WEIGHT;
	}

	@Override
	public double max_total_waiting_time() {
		return max_waiting_time() * 3;
	}

	@Override
	public int max_total_changes() {
		return 8;
	}

	@Override
	public double max_total_walkable_distance() {
		return Math.max(distance * 0.01, 1500);
	}
}
