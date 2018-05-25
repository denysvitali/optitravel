package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.PaginatedList;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.params.PlannerParams;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Algorithm;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;
import com.oracle.tools.packager.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Planner {
    private Coordinate from;
    private Coordinate to;
    private List<Plan> plans = new ArrayList<>();
    private LocalDateTime start_time;
    private boolean alreadyComputed = false;
    private static final Logger Log = Logger.getLogger(Planner.class.getName());


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

        List<Stop> stops = new ArrayList<>();
        BoundingBox boundingBox = new BoundingBox(from, to);
        boundingBox = boundingBox.expand(1500); // Expand the BB by 1500 meters

		// TODO: Handle the case when there are no SSPs (or trips) in a BBox
		// TODO: Dynamically switch between GTFS and TL

        /*try {
            List<ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop>
                    tl_stops = dg.getwTL().getStopsByBBox(boundingBox);
            stops.addAll(tl_stops);
        } catch (TransitLandAPIError transitLandAPIError) {
            transitLandAPIError.printStackTrace();
        }*/

        List<ch.supsi.dti.i2b.shrug.optitravel.models.Trip>
				trips = dg.getTrips(boundingBox);

        List<StopTime> stop_times = new ArrayList<>();

        for(ch.supsi.dti.i2b.shrug.optitravel.models.Trip t : trips){
        	stop_times.addAll(
				t.getStopTrip().stream().map(e ->
						new StopTime(e.getStop(), e.getArrival(), t)
				).collect(Collectors.toList())
			);
		}

		System.out.println("Routing w/ " + stop_times.size() + " StopTimes");

        // stop_times.forEach(e -> System.out.println(e.getStop() + "@" + e.getTime()));

		/*
        List<StopTime> o_d = new ArrayList<>(stop_times);
        sort_by_distancetime(o_d, from);

        List<StopTime> d_d = new ArrayList<>(stop_times);
        sort_by_distance(d_d, to);

        HashMap<Stop, List<StopTime>> stop_stoptime = new HashMap<>();
        stop_times.forEach(e->{
        	stop_stoptime.computeIfAbsent(e.getStop(), k->new ArrayList<>());
            stop_stoptime.get(e.getStop()).add(
            		new StopTime(e.getStop(), e.getTime(), e.getTrip())
			);
        });

        stop_stoptime.values().forEach(e -> {
        	e.sort((a,b)-> Time.compareTo(a.getTime(),b.getTime()));
		});

        HashMap<Node<StopTime>, List<Node<StopTime>>> stop_stop_association = new HashMap<>();
        HashMap<StopTime, Node<StopTime>> node_stoptime = new HashMap<>();

        trips.forEach(t -> {
            StopTime prevStop = null;
            for(StopTrip st : t.getStopTrip()){
                StopTime npStop = new StopTime(st.getStop(), st.getArrival());
                npStop.setTrip(t);
                if(prevStop != null){

                	if(node_stoptime.get(prevStop) == null){
                		node_stoptime.put(prevStop, new Node<>(prevStop));
					}

					Node<StopTime> pNST = node_stoptime.get(prevStop);

                	if(node_stoptime.get(npStop) == null){
                		node_stoptime.put(npStop, new Node<>(npStop));
					}

                	Node<StopTime> nNST = node_stoptime.get(npStop);

					stop_stop_association.computeIfAbsent(
							pNST, k -> new ArrayList<>()
					);

                    stop_stop_association.get(pNST).add(nNST);

                    pNST.addNeighbour(nNST, 0); // Directly connected!
					//System.out.println("CS: " + npStop);
					//System.out.println("PS: " + prevStop);
                }
                prevStop = npStop;
            }
        });

        Stop startingStop = o_d.get(0).getStop();

		Algorithm<StopTime> algorithm = new Algorithm<>();

		for(Node<StopTime> nst : stop_stop_association.keySet()){
			List<Node<StopTime>> lnst = stop_stop_association.get(nst);
			if(nst.isComputedNeighbours()){
				continue;
			}
			for(Node<StopTime> nst_n : lnst){
				{

					double weight = 0.0;
					double wait_time = Time.diffMinutes(nst_n.getElement().getTime(),
							nst.getElement().getTime());

					if(wait_time < 0){
						continue;
					}

					double distance = Distance.distance(
							nst.getElement().getCoordinate(),
							nst_n.getElement().getCoordinate()
					);

					if (wait_time > PlannerParams.MAX_WAITING_TIME) {
						continue;
					}

					if (!nst_n.getElement().getTime().isAfter(nst.getElement().getTime())) {
						continue; // Don't add stops from the past!
					}


					if (!nst.getElement().getTrip().equals(nst_n.getElement().getTrip())) {
						weight += wait_time * PlannerParams.W_WAITING;
						weight += PlannerParams.W_CHANGE;
					} else {
						weight += wait_time * PlannerParams.W_MOVING;
					}

					if(nst.getH() == -1){
						nst.setH(Distance.distance(nst.getElement().getCoordinate(),
								to));
					}

					if(nst_n.getH() == -1){
						nst_n.setH(Distance.distance(nst_n.getElement().getCoordinate(),
								to));
					}

					if(nst_n.equals(nst)){
						// Don't add.
						continue;
					}

					nst.addNeighbour(nst_n, weight);
				}


				// Compute neighbours
				addNeighbours(nst, stop_times, node_stoptime);
				addNeighbours(nst_n, stop_times, node_stoptime);
			}
			nst.setComputedNeighbours(true);
		}*/

		double minutes = 0.0;
		minutes += (PlannerParams.WALKABLE_RADIUS_METERS/PlannerParams.WALK_SPEED_MPS) / 60;
		minutes += PlannerParams.MAX_WAITING_TIME;

		Time s_time = new Time(start_time.format(DateTimeFormatter.ISO_TIME));
		Time s_time2 = Time.addMinutes(s_time, (int) minutes);

		Node<StopTime> startingNST = new Node<>(
				new WalkStopTime(from, s_time)
		);

		Algorithm<StopTime, Location> algorithm = new Algorithm<>(dg);
		HashMap<String, Location> uid_location_hm = algorithm.getUidLocationHM();

		/*PaginatedList<StopTimes> walkableStopTimes;
		try {
			walkableStopTimes = dg.getwGTFS().getStopTimesBetween(s_time, s_time2, from,
					PlannerParams.SOURCE_RADIUS);
			List<Node<StopTime>> wst = (List<Node<StopTime>>) walkableStopTimes.getResult().stream().map(e -> {
				if (uid_location_hm.get(e.stop) == null) {
					try {
						uid_location_hm.put(e.stop, dg.getwGTFS().getStop(e.stop));
					} catch (GTFSrsError gtfSrsError) {
						return null;
					}
				}

				return e.time.stream().map(et -> {
					StopTime st = new StopTime(
							(Stop) uid_location_hm.get(e.stop),
							new Time(et.time));
					st.setTrip(new WalkingTrip(startingNST.getElement(), st));
					return new Node<>(st);
				}).collect(Collectors.toList());
			}).filter(Objects::nonNull).flatMap(List::stream).collect(Collectors.toList());
			System.out.println(wst);
		} catch (GTFSrsError gtfSrsError) {
			gtfSrsError.printStackTrace();
		}*/


		List<Node<StopTime>> path =
				algorithm.route(
						startingNST,
						to
				);

		System.out.println(path);
		alreadyComputed = true;

		List<ch.supsi.dti.i2b.shrug.optitravel.models.Trip> plan_trips =
				path.stream()
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

    private void addNeighbours(Node<StopTime> nst, List<StopTime> stop_times, HashMap<StopTime, Node<StopTime>> node_stoptime){
    	if(nst.isComputedNeighbours()){
    		return;
		}
		List<StopTime> near = new ArrayList<>(stop_times);
		near = near.stream()
				.filter((e)-> e.getTime().isAfter(nst.getElement().getTime()) && !nst.getElement().equals(e))
				.collect(Collectors.toList());
		for(StopTime st : near){
			double weight = 0.0;
			double stop_distance_time = Time.diffMinutes(
					nst.getElement().getTime(),
					st.getTime()
			);

			if(stop_distance_time < 0){
				continue;
			}

			if(nst.getElement().getStop().equals(st.getStop())){
				// Same Stop, different times!
				if(!st.getTime().isAfter(nst.getElement().getTime())){
					continue;
				}
				weight += PlannerParams.W_CHANGE;
				weight += stop_distance_time * PlannerParams.W_WAITING;
				nst.addNeighbour(nst, weight);
				continue;
			}

			addWalkPathToCoordinate(node_stoptime.get(st), to);

			double walk_distance = Distance.distance(
					nst.getElement().getCoordinate(),
					st.getCoordinate());

			if(walk_distance < PlannerParams.WALKABLE_RADIUS_METERS){
				double walk_time_s = walk_distance / PlannerParams.WALK_SPEED_MPS;
				double walk_minutes = walk_time_s / 60;
				double waiting_minutes = stop_distance_time - walk_minutes;

				if(waiting_minutes < 0){
					// Unreachable
					continue;
				}

				if(waiting_minutes > PlannerParams.MAX_WAITING_TIME){
					continue;
				}

				if(waiting_minutes < 1){
					// The stops are close-by, but the change is hard
					weight += PlannerParams.W_FAST_CHANGE;
				}


				weight += PlannerParams.W_WALK * walk_minutes +
						PlannerParams.W_WAITING * waiting_minutes;

				Node<StopTime> new_walking_node = new Node<>(st);
				new_walking_node.getElement().setTrip(new WalkingTrip(
						nst.getElement(), st
				));

				new_walking_node.setH(Distance.distance(
						st.getCoordinate(), to
				));

				nst.addNeighbour(new_walking_node, weight);
				node_stoptime.put(st, new_walking_node);

				System.out.println("Connecting " + new_walking_node.getElement()
						+ " w/ " + nst.getElement() + " because " +
						"they're close-by.");
			}
		}
		nst.setComputedNeighbours(true);
	}

	private Node<StopTime> addWalkPathToCoordinate(Node<StopTime> nst, Coordinate to) {
		double weight = 0.0;

		double walk_distance = Distance.distance(
				nst.getElement().getCoordinate(),
				to);

		if (walk_distance < PlannerParams.WALKABLE_RADIUS_METERS) {
			double walk_time_s = walk_distance / PlannerParams.WALK_SPEED_MPS;
			double walk_minutes = walk_time_s / 60;


			weight += PlannerParams.W_WALK * walk_minutes;

			Node<StopTime> new_walking_node = new Node<>(new WalkStopTime(to));
			new_walking_node.getElement().setTrip(new WalkingTrip(
					nst.getElement(), new_walking_node.getElement()
			));

			new_walking_node.setH(Distance.distance(
					nst.getElement().getCoordinate(), to
			));

			nst.addNeighbour(new_walking_node, weight);
			return new_walking_node;
		}
		return null;
	}

	private Node<StopTime> addWalkpathToNode(Node<StopTime> source, Node<StopTime> dest) {
		double weight = 0.0;
		double walk_distance = Distance.distance(
				source.getElement().getCoordinate(),
				to);

		double stop_distance_time = Time.diffMinutes(
				source.getElement().getTime(),
				dest.getElement().getTime()
		);


		if (walk_distance < PlannerParams.WALKABLE_RADIUS_METERS) {
			double walk_time_s = walk_distance / PlannerParams.WALK_SPEED_MPS;
			double walk_minutes = walk_time_s / 60;
			double waiting_minutes = stop_distance_time - walk_minutes;

			if (waiting_minutes < 0) {
				// Unreachable
				return null;
			}

			if (waiting_minutes > PlannerParams.MAX_WAITING_TIME) {
				return null;
			}

			if (waiting_minutes < 1) {
				// The stops are close-by, but the change is hard
				weight += PlannerParams.W_FAST_CHANGE;
			}


			weight += PlannerParams.W_WALK * walk_minutes +
					PlannerParams.W_WAITING * waiting_minutes;

			Node<StopTime> new_walking_node = dest;
			new_walking_node.getElement().setTrip(new WalkingTrip(
					source.getElement(), dest.getElement()
			));

			new_walking_node.setH(Distance.distance(
					source.getElement().getCoordinate(), to
			));

			source.addNeighbour(new_walking_node, weight);
			return new_walking_node;
		}
		return null;
	}

	private void sort_by_distance(List<StopTime> list, Coordinate target) {
        list.sort((a,b)->{
            double d_a = Distance.distance(
                    a.getLocation().getCoordinate(),
                    target
            );

            double d_b = Distance.distance(
                    b.getLocation().getCoordinate(),
                    target
            );

            return Double.compare(d_a, d_b);

        });
    }

    private void sort_by_distancetime(List<StopTime> list, Coordinate target) {
        list.sort((a,b)->{
            double d_a = Distance.distance(
                    a.getLocation().getCoordinate(),
                    target
            );

            double d_b = Distance.distance(
                    b.getLocation().getCoordinate(),
                    target
            );

            int dc = Double.compare(d_a, d_b);
            if(dc == 0){
                return Time.compareTo(a.getTime(), b.getTime());
            } else {
                return dc;
            }

        });
    }

    public void setStartTime(LocalDateTime ldt) {
        start_time = ldt;
    }
}
