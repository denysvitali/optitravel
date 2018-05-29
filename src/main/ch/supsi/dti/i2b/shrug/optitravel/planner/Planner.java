package ch.supsi.dti.i2b.shrug.optitravel.planner;


import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.params.PlannerParams;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Algorithm;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Planner<T extends TimedLocation, L extends Location> {
    private Coordinate from;
    private Coordinate to;
    private List<Plan> plans = new ArrayList<>();
    private LocalDateTime start_time;
    private boolean alreadyComputed = false;
    private static final Logger Log = Logger.getLogger(Planner.class.getName());

    private DataGathering dg;
	private Algorithm<T, L> algorithm;


	Planner(Coordinate from, Coordinate to){
        this.from = from;
        this.to = to;
    }

    List<Plan> getPlans(){
        if(!alreadyComputed){
            computePlans();
        }
        return plans;
    }

    public void computePlans() {
        if(start_time == null){
            // Start Time is null, assuming we're planning from NOW.
            start_time = LocalDateTime.now();
			Log.warning(
                    "computePlans() called w/o a start_time, setting to NOW (" +
                            start_time.format(DateTimeFormatter.ISO_DATE)
                            + ")"
            );
		}
		DataGathering dg = new DataGathering();
		this.dg = dg;
		
		Algorithm<T, L> algorithm = new Algorithm<>(dg);
		this.algorithm = algorithm;

		BoundingBox boundingBox = new BoundingBox(from, to);
		boundingBox = boundingBox.expand(1500); // Expand the BB by 1500 meters

		// TODO: Handle the case when there are no SSPs (or trips) in a BBox
		// TODO: Dynamically switch between GTFS and TL

		List<Trip>
				trips = dg.getTrips(boundingBox);

		/*List<StopTime> stop_times = new ArrayList<>();

		for(Trip t : trips){
			stop_times.addAll(
				t.getStopTrip().stream().map(e ->
						new StopTime(e.getStop(), e.getArrival(), t)
				).collect(Collectors.toList())
			);
		}

		System.out.println("Routing w/ " + stop_times.size() + " StopTimes");*/

		double minutes = 0.0;
		minutes += (PlannerParams.WALKABLE_RADIUS_METERS/PlannerParams.WALK_SPEED_MPS) / 60;
		minutes += PlannerParams.MAX_WAITING_TIME;

		Time s_time = new Time(start_time.format(DateTimeFormatter.ISO_TIME));

		Node<T, L> startingNST = new Node<T, L>(
				(T) new WalkStopTime(from, s_time)
		);
		startingNST.setH(Distance.distance(from, to));
		startingNST.setDg(dg);
		startingNST.setAlgorithm(algorithm);

		List<Node<T, L>> path =
		algorithm.route(
				startingNST,
				to
		);

		System.out.println(path);
		assert(path!=null);
		alreadyComputed = true;

		List<Trip> plan_trips = path.stream()
						.map((e)->e.getElement().getTrip())
						.distinct()
						.collect(Collectors.toList());

		Plan p = new Plan(plan_trips,
				path.get(0).getElement().getTime(),
				path.get(path.size()-1).getElement().getTime(),
				path.get(0).getElement().getCoordinate(),
				path.get(path.size()-1).getElement().getCoordinate()
				);

		plans.add(p);
    }

    public void setStartTime(LocalDateTime ldt) {
        start_time = ldt;
    }
}
