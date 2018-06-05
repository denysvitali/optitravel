package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.StopDistance;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.PaginatedList;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.LineString;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.models.Date;
import ch.supsi.dti.i2b.shrug.optitravel.params.DefaultPlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.params.PlannerParams;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Algorithm;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;

import java.util.*;
import java.util.stream.Collectors;

public class DataGathering{
	private static final double AVG_MOVING_SPEED_KMH = 8;
	private static final double AVG_MOVING_SPEED = AVG_MOVING_SPEED_KMH / 60 * 1000; // in m/minute
	private TransitLandAPIWrapper wTL = new TransitLandAPIWrapper();
    private GTFSrsWrapper wGTFS = new GTFSrsWrapper();
    private PubliBikeWrapper wPB = new PubliBikeWrapper();

    private List<Trip> trips = new ArrayList<>();
    private List<Stop> stops = new ArrayList<>();
    private List<StopTimes> stop_times = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();

    private HashMap<Stop, List<TripTimeStop>> trip_time_stop_by_stop = new HashMap<>();
    private HashMap<String, Stop> stop_by_uid = new HashMap<>();

    private PlanPreference pp = new DefaultPlanPreference();

    private Date from_date;
    private Time start_time;
    private Coordinate source;
    private Coordinate destination;

	DataGathering(){

    }

	public void setFromDate(Date from_date) {
		this.from_date = from_date;
	}

	public void setStartTime(Time start_time) {
		this.start_time = start_time;
	}

	public void setDestination(Coordinate destination) {
		this.destination = destination;
	}

	public void setSource(Coordinate source) {
		this.source = source;
	}

	public void setPlanPreference(PlanPreference pp) {
		this.pp = pp;
	}

	public PlanPreference getPlanPreference() {
		return pp;
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
			// TODO: Add TL condition
			//stops.addAll(getwTL().getStopsByBBox(boundingBox));
		} catch(GTFSrsError err){
			err.printStackTrace();
		}

		return stops;
	}

	public List<Trip> getTrips(BoundingBox boundingBox) {
		if(trips.size() != 0){
			return trips;
		}

		int max_travel_minutes = 0;
		max_travel_minutes += getPlanPreference().max_total_waiting_time();
		max_travel_minutes += Math.round(Distance.distance(source, destination) / AVG_MOVING_SPEED + 10);
		max_travel_minutes += Math.round(pp.max_total_walkable_distance() / (pp.walk_speed_mps() * 60));
		Time end_time = Time.addMinutes(start_time, max_travel_minutes);


		try{

			TripSearch ts = new TripSearch();
			ts.departure_after = start_time.toString();
			ts.arrival_before = end_time.toString();

			PaginatedList<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip>
					gtfs_paginated_trips = getwGTFS().getTripsByBBox(boundingBox, ts);
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
		HashMap<Node<T,L>, Double> neighbours = new HashMap<>();

		trips.forEach(t->{
			for(StopTrip st : t.getStopTrip()){
				StopTime st_st = new StopTime(st.getStop(), st.getArrival());
				if(st.getStop().equals(currentNode.getElement().getLocation())){

					if(t.getStopTrip().indexOf(st) != (t.getStopTrip().size() - 1)){

						int m_index = t.getStopTrip().indexOf(st);
						StopTrip cst = t.getStopTrip().get(m_index);
						StopTrip nst = t.getStopTrip().get(m_index+1);

						Time cst_time = cst.getDeparture();
						Time cn_time = currentNode.getElement().getTime();

						if(st_st.equals(currentNode.getElement())){
							// Our node is the current st_st (wait time is 0)

							if(!Time.isAfter(cst_time, cn_time)){
								// We can't reach this stop (wait time is negative!)
								return;
							}

							double wait_time = Time.diffMinutes(cst_time, cn_time);
							assert(wait_time >= 0); // Paranoic

							if(currentNode.getChanges() + 1 > pp.max_total_changes()){
								// Max changes exceeded!
								return;
							}
							StopTime newStopSt = new StopTime(nst.getStop(), nst.getArrival());
							newStopSt.setTrip(t);
							Node<T,L> newStop = new Node<>((T) newStopSt);
							newStop.setDg(this);
							newStop.setChanges(currentNode.getChanges() + 1);
							newStop.setH(Distance.distance(
									nst.getStop().getCoordinate(),
									destination
							));
							newStop.setWaitTotal(currentNode.getWaitTotal());
							newStop.setFrom(currentNode);
							newStop.setAlgorithm(algorithm);
							addCalculatedNeighbour(currentNode, neighbours, newStop);
						}
						else {

							if(!Time.isAfter(cst_time, cn_time)){
								// We can't reach this stop (wait time is negative!)
								return;
							}

							double wait_time = Time.diffMinutes(cst_time, cn_time);
							assert(wait_time >= 0); // Paranoic

							if(currentNode.getWaitTotal() + wait_time > pp.max_total_waiting_time()){
								// Max waiting time exceeded!
								return;
							}

							if(currentNode.getElement().getTrip() instanceof WaitingTrip){
								// Won't wait twice (avoid graph node loops)
								return;
							}

							StopTime newSameStopSt = new StopTime(cst.getStop(), cst.getArrival());
							newSameStopSt.setTrip(new WaitingTrip(cst.getStop(), wait_time));
							Node<T,L> newSameStopNode = new Node<>((T) newSameStopSt);
							newSameStopNode.setDg(this);
							newSameStopNode.setH(currentNode.getH());
							newSameStopNode.setWaitTotal(currentNode.getWaitTotal() + wait_time);
							newSameStopNode.setFrom(currentNode);
							newSameStopNode.setChanges(currentNode.getChanges());
							newSameStopNode.setAlgorithm(algorithm);
							addCalculatedNeighbour(currentNode, neighbours, newSameStopNode);
						}
					}
				}
			}
		});
//
//		stop_times.stream()
//				.filter(e->currentNode.getElement().getLocation().equals(stop_by_uid.get(e.getStop())))
//				.forEach(e->{
//			Stop s = stop_by_uid.get(e.getStop());
//			if(s == null){
//				return;
//			}
//			if(s.equals(currentNode.getElement().getLocation())){
//				// The stop is the same, but the times are different
//
//				trips.forEach(t -> {
//					ArrayList<StopTime> st = new ArrayList<>();
//					for(StopTrip strip : t.getStopTrip()){
//						if(strip.getStop().equals(s)){
//							System.out.println("OUR STOP!");
//						}
//					}
//
//					/*
//					StopTime st = new StopTime(s,tts.getTime());
//					// Don't include myself
//					if(currentNode.getElement().equals(st)){
//						return;
//					}
//
//					// Keep only trips that are different from the current one
//					// (= we don't allow to get to the same trip / route to be an allowed change)
//					Trip t = new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip(tts.getTrip());
//					if(	!currentNode.getElement().getTrip().equals(t)
//							&& !currentNode.getElement().getTrip().getRoute().equals(t.getRoute())
//					){
//						System.out.println("Different trip / route!");
//						double time_diff = Time.diffMinutes(tts.getTime(), currentNode.getElement().getTime());
//						if(time_diff > pp.max_waiting_time()){
//							return;
//						}
//						if(currentNode.getWaitTotal() + time_diff > pp.max_total_waiting_time()){
//							return;
//						}
//
//						Node<T,L> node = new Node<>((T) st);
//						node.setH(Distance.distance(s.getCoordinate(), destination));
//						node.setFrom(currentNode);
//						node.setDg(this);
//						node.setChanges(currentNode.getChanges() + 1); // We changed trip!
//						node.setWaitTotal(currentNode.getWaitTotal() + time_diff);
//						addCalculatedNeighbour(currentNode, neighbours, node);
//					}
//					else {
//						System.out.println("We're on the same Trip (BCS!) :)");
//						Node<T,L> node = new Node<>((T) st);
//						node.setH(Distance.distance(s.getCoordinate(), destination));
//						node.setFrom(currentNode);
//						node.setDg(this);
//						node.setChanges(currentNode.getChanges());
//						node.setWaitTotal(currentNode.getWaitTotal());
//						addCalculatedNeighbour(currentNode, neighbours, node);
//					}
//					*/
//				});
//			}
//		});

		// Walking Trips (add stops that can be reached by walking)
		stops.stream()
				.filter(e-> {
							double d = Distance.distance(e.getCoordinate(), currentNode.getElement().getCoordinate());
							//System.out.println(e + " - " + currentNode.getElement() + ": " + d);
							return d < pp.walkable_radius_meters();
						}
					)
				.filter(e->!e.equals(currentNode.getElement().getLocation()))
				.forEach(s -> {
					double distance = Distance.distance(s.getCoordinate(), currentNode.getElement().getCoordinate());
					double walk_minutes = (pp.walk_speed_mps() * distance)/60.0;
					Time t = Time.addMinutes(
							currentNode.getElement().getTime(),
							(int) walk_minutes
					);
					StopTime st = new StopTime(s, t);
					st.setTrip(new WalkingTrip((StopTime) currentNode.getElement(), st));
					Node<T,L> node = new Node<>((T) st);
					node.setDg(this);
					node.setH(distance);
					node.setWaitTotal(currentNode.getWaitTotal());
					if(currentNode.getChanges() + 1 > pp.max_total_changes()){
						return;
					}
					node.setChanges(currentNode.getChanges() + 1);
					node.setFrom(currentNode);
					addCalculatedNeighbour(currentNode, neighbours, node);
				});

		return neighbours;

//
//
//    	HashMap<L, ArrayList<T>> timedlocation_by_location =
//				algorithm.getTimedLocationByLocation();
//
//		double max_minutes = (
//    			getPlanPreference().walkable_radius_meters()/
//				getPlanPreference().walk_speed_mps()
//		) / 60;
//
//		int total_time = (int) (Math.ceil(max_minutes) +
//				getPlanPreference().max_waiting_time());
//
//		Time t = currentNode.getElement().getTime();
//		Time t_max = Time.addMinutes(
//    			currentNode.getElement().getTime(),
//				total_time
//		);
//		Coordinate s = currentNode.getElement().getCoordinate();
//
//    	/*
//    		Get Stops from GTFS
//    	 */
//
//		HashMap<String, ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop>
//				uid_stop_hm =
//				(HashMap<String, ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop>)
//						algorithm.getUidLocationHM();
//
//
//
//    	if(currentNode.getElement() instanceof StopTime){
//    		StopTime st = (StopTime) currentNode.getElement();
//			if(st.getStop() instanceof ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop){
//				// Get connected stops for GTFS
//				ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop gtfs_stop = (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop)
//						st.getStop();
//				try {
//					StopTimes stopTimes = wGTFS.getStopTimesBetween(t, t_max, from_date, gtfs_stop);
//					if(stopTimes != null) {
//						stopTimes
//								.getTime()
//								.stream()
//								.map(tt -> {
//									Stop next_stop = (Stop) algorithm.getUidLocationHM().get(tt.getNextStop());
//									if (next_stop == null) {
//										try {
//											next_stop = getwGTFS().getStop(tt.getNextStop());
//											algorithm.getUidLocationHM()
//													.put(tt.getNextStop(), (L) next_stop);
//										} catch (GTFSrsError gtfSrsError) {
//											return null;
//										}
//									}
//									ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip newTrip
//											= new ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip(tt.getTrip());
//
//
//									StopTime next_stoptime = new StopTime(
//											next_stop,
//											tt.getTime(),
//											newTrip
//									);
//
//									if (st.equals(next_stoptime) && newTrip.equals(
//											st.getTrip())
//									) {
//										// https://arxiv.org/pdf/1607.01299.pdf
//										// Page 3:
//										// "We require that trips never overtake
//										//  another trip of the same line;"
//										return null;
//									}
//
//									Node n = new Node<T, L>((T) next_stoptime);
//									n.setFrom(currentNode);
//
//									n.setH(Distance.distance(
//											algorithm.getDestination(),
//											next_stop.getCoordinate())
//									);
//									n.setWaitTotal(currentNode.getWaitTotal());
//									n.setChanges(currentNode.getChanges());
//									n.setDg(this);
//									n.setAlgorithm(algorithm);
//									return n;
//								})
//								.filter(Objects::nonNull)
//								.forEach(e -> {
//									double weight = Time.diffMinutes(
//											e.getElement().getTime(),
//											t) * getPlanPreference().w_moving();
//									if (!e.getElement().getTrip().equals(currentNode.getElement().getTrip())) {
//										// Trip Changed!
//										if(currentNode.getChanges() + 1 > pp.max_total_changes()){
//											return;
//										}
//										e.setChanges(currentNode.getChanges() + 1);
//										weight += getPlanPreference().w_change();
//									}
//									neighbours.put(e, weight);
//								});
//					}
//				} catch (GTFSrsError gtfSrsError) {
//					gtfSrsError.printStackTrace();
//				}
//			}
//		}
//
//		System.out.println("Computed neighbours, adding walking stops");
//
//    	/* Walkable Stops */
//
//		try {
//			List<StopDistance> st = wGTFS
//					.getStopsNear(currentNode.getElement().getCoordinate(),
//							getPlanPreference().walkable_radius_meters())
//					.getResult();
//			List<Node<T, L>> result = st.stream().map(e -> {
//				ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop es = e.getStop();
//				uid_stop_hm.putIfAbsent(es.getUid(), es);
//
//				timedlocation_by_location.computeIfAbsent(
//						(L) es,
//						k -> new ArrayList<>()
//				);
//
//				double distance =
//						Distance.distance(es.getCoordinate(),
//								currentNode.getElement().getCoordinate());
//
//				double walk_seconds = distance * getPlanPreference().walk_speed_mps();
//				Time start = t;
//				Time.addMinutes(start, (int) Math.ceil(walk_seconds / 60.0));
//
//						StopTime tl = new StopTime(es, start);
//						tl.setTrip(new WalkingTrip((StopTime)
//								currentNode.getElement(),
//								tl));
//						Node<T, L> nst = new Node<>((T) tl);
//						nst.setWaitTotal(currentNode.getWaitTotal());
//						nst.setChanges(currentNode.getChanges());
//						nst.setDg(this);
//						nst.setAlgorithm(algorithm);
//						nst.setH(distance);
//						return nst;
//					}
//			).collect(Collectors.toList());
//
//			result
//						.stream()
//						.filter(n-> !(n.equals(currentNode)))
//						.filter(n-> !algorithm.getVisited().contains(n))
//						.peek((n) -> n.setFrom(currentNode))
//						.map((Node<T,L> n) -> calculateWeight(currentNode, n, algorithm.getDestination()))
//						.filter(Objects::nonNull)
//						.filter(e -> !currentNode.equals(e.getKey()))
//						.forEach(e->{
//							neighbours.putIfAbsent(e.getKey(),
//									e.getValue());
//						});
//			currentNode.setComputedNeighbours(true);
//			return neighbours;
//		} catch (GTFSrsError gtfSrsError) {
//			gtfSrsError.printStackTrace();
//		}
//
//		currentNode.setComputedNeighbours(true);
//		return neighbours;
	}

	private <T extends TimedLocation, L extends Location> void addCalculatedNeighbour(Node<T, L> currentNode, HashMap<Node<T, L>, Double> neighbours, Node<T, L> node) {
		Map.Entry<Node<T, L>, Double> cw =
				calculateWeight(currentNode, node, destination);
		if (cw != null) {
			neighbours.putIfAbsent(cw.getKey(), cw.getValue());
		}
	}

	private <T extends TimedLocation, L extends Location> Map.Entry<Node<T,L>, Double>
	 calculateWeight(Node<T,L> c, Node<T,L> n, Coordinate d) {
    	double weight = 0.0;
    	boolean same_trip = true;
    	/*weight += Distance.distance(c.getElement().getCoordinate(),
    	n.getElement().getCoordinate());*/

    	T ce = c.getElement();
    	T ne = n.getElement();

    	if(ce == null || ne == null){
    		return null;
		}

		if(ce.getTrip() != null && ne.getTrip() != null){
			if(ce.getTrip().equals(ne.getTrip())){
				System.out.println("Same trip :)" + ce.getLocation() + ", " + ne.getLocation());
			} else {

				weight += getPlanPreference().w_change();
				weight += Distance.distance(c.getElement().getCoordinate(),
						n.getElement().getCoordinate());
				same_trip = false;
				//System.out.println(ce.getTrip() + ", " + ne.getTrip());
			}
		} else {
			weight += getPlanPreference().w_change();
			weight += Distance.distance(c.getElement().getCoordinate(),
					n.getElement().getCoordinate());
			same_trip = false;
			//System.out.println(ce.getTrip() + ", " + ne.getTrip());
		}

		Location cl = c.getElement().getLocation();
		Location nl = n.getElement().getLocation();

		if(cl instanceof Stop && nl instanceof Stop &&
				cl.getClass().equals(nl.getClass()))
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

		if(c.getWaitTotal() + minute_wait > pp.max_total_waiting_time()){
			// Waiting time limit exceeded
			return null;
		}

		if(!same_trip){
			if(n.getChanges() + 1 > pp.max_total_changes()){
				System.out.println("Changes count ecceeded!");
				return null;
			}
			n.addChange();
		}

		n.setWaitTotal(c.getWaitTotal());
		n.addToWaitTotal(minute_wait);
		weight += getPlanPreference().w_waiting() * minute_wait;

		n.setH(
				Distance.distance(nl.getCoordinate(), d));

		// Return the weighted arc.

		return new AbstractMap.SimpleEntry<>(n, weight);
	}

	public Trip fetchTrip(Trip e) {
    	if(e instanceof ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip){
			ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip t = (ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip) e;
			try {
				return wGTFS.getTrip(t.getUID());
			} catch (GTFSrsError gtfSrsError) {
				gtfSrsError.printStackTrace();
			}
		}
		return e;
	}

	public void fetchData(){
		BoundingBox boundingBox = new BoundingBox(source, destination);
		double distance = Distance.distance(source, destination);
		boundingBox = boundingBox.expand(distance*1 + 100);

		System.out.println("Bouding Box is: " + boundingBox.toPostGIS());

		trips = getTrips(boundingBox);
		stops = getStops(boundingBox);
		stop_times = getStopTimes(boundingBox);

		stops.forEach(e->{
			stop_by_uid.putIfAbsent(e.getUid(), e);
		});

		stop_times.forEach(e->{
			trip_time_stop_by_stop.put(
					stop_by_uid.get(
							e.getStop()),
							e.getTime()
			);
		});

	}

	private List<StopTimes> getStopTimes(BoundingBox boundingBox) {
		if(stop_times.size() != 0){
			return stop_times;
		}

		int max_travel_minutes = 0;
		max_travel_minutes += getPlanPreference().max_total_waiting_time();
		max_travel_minutes += Math.round(Distance.distance(source, destination) / AVG_MOVING_SPEED);
		Time end_time = Time.addMinutes(start_time, max_travel_minutes);

		try{

			PaginatedList<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes>
					gtfs_paginated_stop_times = getwGTFS().getStopTimesInBBoxBetween(boundingBox,
					start_time, end_time);
			List<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes>
					gtfs_stop_times = gtfs_paginated_stop_times.getResult();

			stop_times.addAll(gtfs_stop_times);
			// TODO: Add TL
		} catch(GTFSrsError err){
			err.printStackTrace();
		}

		return stop_times;
	}
}