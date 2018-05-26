package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.PaginatedList;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.params.PlannerParams;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Algorithm;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;

import java.util.*;
import java.util.stream.Collectors;

public class DataGathering{
    private TransitLandAPIWrapper wTL = new TransitLandAPIWrapper();
    private GTFSrsWrapper wGTFS = new GTFSrsWrapper();
    private PubliBikeWrapper wPB = new PubliBikeWrapper();

    private List<Trip> trips = new ArrayList<>();
    private List<Stop> stops = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();

    DataGathering(){

    }

    public GTFSrsWrapper getwGTFS() {
        return wGTFS;
    }

    public PubliBikeWrapper getwPB() {
        return wPB;
    }

    public TransitLandAPIWrapper getwTL() {
        return wTL;
    }

    public List<Stop> getStops(BoundingBox boundingBox){
    	if(stops.size() != 0){
    		return stops;
		}
    	try{
			stops.addAll(getwGTFS().getStopsByBBox(boundingBox));
			stops.addAll(getwTL().getStopsByBBox(boundingBox));
		} catch(GTFSrsError | TransitLandAPIError err){
			err.printStackTrace();
		}

		return stops;
	}

	public List<Trip> getTrips(BoundingBox boundingBox) {
		if(trips.size() != 0){
			return trips;
		}
		try{

			PaginatedList<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip>
					gtfs_paginated_trips = getwGTFS().getTripsByBBox(boundingBox);
			List<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip>
					gtfs_trips = gtfs_paginated_trips.getResult();

			trips.addAll(gtfs_trips);
			//trips.addAll(getwTL().getTripsByBBox(boundingBox));
			// TODO: Add TL
		} catch(GTFSrsError /*| TransitLandAPIError*/ err){
			err.printStackTrace();
		}

		return trips;
	}

	public <T extends TimedLocation, L extends Location> HashMap<Node<T,L>, Double>
		getNeighbours(Node<T,L> currentNode, Algorithm<T,L> algorithm) {

    	HashMap<L, ArrayList<T>> timedlocation_by_location =
				algorithm.getTimedLocationByLocation();

    	double max_minutes = (
    			PlannerParams.WALKABLE_RADIUS_METERS /
				PlannerParams.WALK_SPEED_MPS
		) / 60;

    	int total_time = (int) (Math.ceil(max_minutes) +
				PlannerParams.MAX_WAITING_TIME);

    	Time t = currentNode.getElement().getTime();
    	Time t_max = Time.addMinutes(
    			currentNode.getElement().getTime(),
				total_time
		);
    	Coordinate s = currentNode.getElement().getCoordinate();

    	/*
    		Get Stops from GTFS
    	 */

    	HashMap<String, ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop>
				uid_stop_hm =
				(HashMap<String, ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop>)
						algorithm.getUidLocationHM();

		try {
			List<StopTimes> st = wGTFS
					.getStopTimesBetween(t, t_max, s, PlannerParams.WALKABLE_RADIUS_METERS)
					.getResult();
			List<Node<T,L>> result = st.stream().map(e -> {

				if(uid_stop_hm.get(e.getStop()) == null){
					try {
						uid_stop_hm.put(e.getStop(), wGTFS.getStop(e.getStop()));
					} catch (GTFSrsError gtfSrsError) {
						return null;
					}
				}

				ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop stop =
						uid_stop_hm.get(e.getStop());

				timedlocation_by_location.computeIfAbsent(
						(L) stop,
						k -> new ArrayList<>()
				);

				return e.getTime().stream().map(
					tt -> {
						ArrayList<T> times =
								timedlocation_by_location.get(stop);
						StopTime tl = new StopTime(stop,
								new Time(tt.time));
						times.add((T) tl);
						tl.setTrip(new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip(tt.trip));
						Node<T,L> nst = new Node<>((T) tl);
						nst.setDg(this);
						nst.setAlgorithm(algorithm);
						return nst;
					}
				).collect(Collectors.toList());
			}).flatMap(Collection::stream).collect(Collectors.toList());

			HashMap<Node<T,L>, Double> neighbours = new HashMap<>
			(
				result
						.stream()
						.filter(n-> !(n.equals(currentNode)))
						.filter(n-> !algorithm.getVisited().contains(n))
						.peek((n) -> n.setFrom(currentNode))
						.map((Node<T,L> n) -> calculateWeight(currentNode, n, algorithm.getDestination()))
						.filter(Objects::nonNull)
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
			);
			currentNode.setComputedNeighbours(true);
			return neighbours;
		} catch (GTFSrsError gtfSrsError) {
			gtfSrsError.printStackTrace();
		}
		return new HashMap<>();
	}

	private static <T extends TimedLocation, L extends Location> Map.Entry<Node<T,L>, Double>
	 calculateWeight(Node<T,L> c, Node<T,L> n, Coordinate d) {
    	double weight = 0.0;
    	boolean same_trip = true;
    	/*weight += Distance.distance(c.getElement().getCoordinate(),
    	n.getElement().getCoordinate());*/

		if(c.getElement().getTrip() == null && n.getElement() != null
		|| c.getElement().getTrip() != null && n.getElement().getTrip() == null ||
		!c.getElement().getTrip().equals(n.getElement().getTrip())){
			weight += PlannerParams.W_CHANGE;
			weight += Distance.distance(c.getElement().getCoordinate(),
					n.getElement().getCoordinate());
			same_trip = false;
		} else {
			System.out.println("Same trip :)");
		}

		if(c.getElement().getLocation() instanceof Stop &&
			n.getElement().getLocation() instanceof  Stop &&
			c.getElement().getLocation().getClass().equals(n.getElement().getLocation().getClass()))
		{
			Stop c_s = (Stop) c.getElement().getLocation();
			Stop n_s = (Stop) n.getElement().getLocation();

			/*
				Watch out!
				----------
				Weight calculation between two stops can only be performed
				if they're the same Stop (but w/ a different
				time and a different trip) or are directly connected by a trip.
				This means that we have the Same Stop, but different StopTimes.
				To connect two stops together, we use a WalkTrip.
				Therefore the following assertion should always be valid.
				E.g:	Lugano - Locarno via Giubiasco
						Lugano (12:56 PM) 	- Giubiasco (1:22 PM)
						Giubiasco (1:34 PM)	- Locarno
				-------------------------------------------------------------
			*/

			if(!(c_s.equals(n_s) || same_trip)){
				return null;
			}
		}

		/*
			Time Weight
			------------
			This weight will define how bad for the user to wait
			at a stop for the difference in time.
		 */
		double minute_wait = Time.diffMinutes(n.getElement().getTime(),
				c.getElement().getTime());
		assert(minute_wait>=0);
		weight += PlannerParams.W_WAITING * minute_wait;

		n.setH(Distance.distance(n.getElement().getLocation().getCoordinate(), d));

		// Return the weighted arc.

		return new AbstractMap.SimpleEntry<>(n, weight);
	}
}