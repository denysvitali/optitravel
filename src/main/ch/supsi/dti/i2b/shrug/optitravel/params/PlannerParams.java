package ch.supsi.dti.i2b.shrug.optitravel.params;

public class PlannerParams {
	// The maximum amount of meters that connect two stops
	public static final int WALKABLE_RADIUS_METERS = 500;
	// Average walking speed (in m/s)
	public static final double WALK_SPEED_MPS = 1.4; // = 5 km/h

	// Maximum Waiting Time (in minutes)
	public static final double MAX_WAITING_TIME = 50.0;

	// Preferences (10 = worst choice, 0 = best choice)
	public static final double PREF_WALK = 8;
	public static final double PREF_WAITING = 7;

	private static final double PREF_WEIGHT = 1000;

	// Weights (ranging from 10 = worse, 0 = best)
	public static final double W_WALK = 8 * PREF_WEIGHT;
	public static final double W_WAITING = 7 * PREF_WEIGHT;

}
