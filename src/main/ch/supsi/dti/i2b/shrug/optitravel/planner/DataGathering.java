package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.PaginatedList;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.LineString;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.models.Date;
import ch.supsi.dti.i2b.shrug.optitravel.params.DefaultPlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Algorithm;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
//			stops.addAll(getwGTFS().getStopsByBBox(boundingBox));
			stops.addAll(getwTL().getStopsByBBox(boundingBox));
		} catch(/*GTFSrsError |*/ TransitLandAPIError err){
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


/*		try{

			TripSearch ts = new TripSearch();
			ts.departure_after = start_time.toString();
			ts.arrival_before = end_time.toString();

			PaginatedList<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip>
					gtfs_paginated_trips = getwGTFS().getTripsByBBox(boundingBox, ts);
			List<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip>
					gtfs_trips = gtfs_paginated_trips.getResult();

			trips.addAll(gtfs_trips);


*/
			/////////////////////////////


			final List<RouteStopPattern> routeStopPatternsInBBox = new ArrayList<>();
			final List<ScheduleStopPair> scheduleStopPairsInBBox = new ArrayList<>();

			AtomicInteger count = new AtomicInteger();

			getwTL().AgetRouteStopPatternsByBBox(boundingBox, (rsps)->{

				System.out.println(rsps.size());
				routeStopPatternsInBBox.addAll(rsps);

				synchronized (getwTL()){
					count.getAndIncrement();
					getwTL().notify();
				}

			});

			getwTL().AgetScheduleStopPairsByBBox(boundingBox, (ssps)->{

				System.out.println(ssps.size());
				scheduleStopPairsInBBox.addAll(ssps);
				synchronized (getwTL()){
					count.getAndIncrement();
					getwTL().notify();
				}

			});


			synchronized (getwTL()){
				while(count.get()!=2){
					try {
						getwTL().wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}


			for(RouteStopPattern rsp : routeStopPatternsInBBox){

				for(String trip_id : rsp.getTrips()){

					List<ScheduleStopPair> schedulesInRspTrip = new ArrayList<>();
					for(ScheduleStopPair sch : scheduleStopPairsInBBox){

						if(sch.getTrip().equals(trip_id) && sch.getRoute_stop_pattern_onestop_id().equals(rsp.getId()))
							schedulesInRspTrip.add(sch);

					}
					if(schedulesInRspTrip.size() != 0) {
                        Trip tlTrip = new ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Trip();
                        ((ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Trip) tlTrip).setTrip_id(trip_id);
                        ((ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Trip) tlTrip).setRoute_stop_pattern_id(rsp.getId());
                        ((ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Trip) tlTrip).setRoute(rsp.getRoute());

                        for (ScheduleStopPair sch : schedulesInRspTrip) {

                            int index = 0;
                            for (String stop_id : rsp.getStopPattern()) {

                                if (stop_id.equals(sch.getOrigin_onestop_id())) {

                                    StopTrip stopTrip = new ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.StopTrip();
                                    ((ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.StopTrip) stopTrip).setStopSequence(index);
                                    ((ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.StopTrip) stopTrip).setStop(stop_by_uid.get(stop_id));
                                    ((ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.StopTrip) stopTrip).setArrival(sch.getArrival());
                                    ((ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.StopTrip) stopTrip).setDeparture(sch.getDeparture());

                                    ((ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Trip) tlTrip).add_stop_sequence(stopTrip);
                                }
                                index++;

                            }

                        }
                        trips.add(tlTrip);
                    }

				}

			}




			//trips.addAll(getwTL().getTripsByBBox(boundingBox));
			// TODO: Add TL
//		} catch(GTFSrsError /*| TransitLandAPIError*/ err){
//			err.printStackTrace();
//		}

		return trips;
	}

	public <T extends TimedLocation, L extends Location> HashMap<Node<T,L>, Double>
		getNeighbours(Node<T,L> currentNode, Algorithm<T,L> algorithm) {
		HashMap<Node<T,L>, Double> neighbours = new HashMap<>();

		// Connected stops (In a Trip)
		for(Trip t : trips){
			int stop_index = t.getStopIndex((Stop) currentNode.getElement().getLocation());
			if(stop_index != -1 && stop_index < t.getStopTrip().size() - 1){
				// This stop is in the trip, and isn't the last one!
				System.out.println("We've got a stop");
				StopTrip cr_stoptrip = t.getStopTrip().get(stop_index);
				StopTrip nx_stoptrip = t.getStopTrip().get(stop_index + 1);

				StopTime trip_el_st = new StopTime(cr_stoptrip.getStop(),
							cr_stoptrip.getDeparture());
				StopTime trip_nx_st = new StopTime(nx_stoptrip.getStop(),
						nx_stoptrip.getDeparture());

				if(!Time.isAfter(trip_el_st.getTime(), currentNode.getElement().getTime()))
				{
					// The starting point isn't after our current node time,
					// therefore we exclude this element
					continue;
				}

				// Wait time, till the next bus / train
				double wait_time = Time.diffMinutes(trip_el_st.getTime(), currentNode.getElement().getTime());
				assert(wait_time>=0); // Assertion, we verified this condition before.

				if(!trip_el_st.equals(currentNode.getElement())){
					// We need to wait at this stop for X minutes,
					// before we can hop on this trip.

					if(currentNode.getWaitTotal() + wait_time > pp.max_total_waiting_time()){
						// Waiting time exceeded
						continue;
					}

					if(wait_time > pp.max_waiting_time()){
						// Can't wait more than the max_waiting_time
						continue;
					}

					Node<T,L> nextTimedStop = new Node<>((T) trip_el_st);
					trip_el_st.setTrip(new WaitingTrip(currentNode.getElement().getLocation(), wait_time));
					nextTimedStop.setAlgorithm(algorithm);
					nextTimedStop.setDg(this);
					nextTimedStop.setH(currentNode.getH());
					nextTimedStop.setChanges(currentNode.getChanges());
					nextTimedStop.setWaitTotal(currentNode.getWaitTotal() + wait_time);
					nextTimedStop.setWalkingTotal(currentNode.getWalkingTotal());
					nextTimedStop.setFrom(currentNode);

					double weight = 0;
					weight += getPlanPreference().w_waiting() * wait_time;

					neighbours.putIfAbsent(nextTimedStop, weight);
					continue;
				}

				assert(wait_time == 0);

				System.out.println("Found a directly connected stop! YAY!");
				Node<T,L> nextConnectedStop = new Node<>((T) trip_nx_st);
				trip_nx_st.setTrip(t);
				nextConnectedStop.setAlgorithm(algorithm);
				nextConnectedStop.setDg(this);
				nextConnectedStop.setH(Distance.distance(trip_nx_st.getCoordinate(),
						destination));

				boolean same_trip = false;
				if(currentNode.getElement().getTrip() != null &&
						currentNode.getElement().getTrip().equals(t)){
						same_trip = true;
				}
				nextConnectedStop.setChanges(currentNode.getChanges() + (same_trip?0:1));
				nextConnectedStop.setWaitTotal(currentNode.getWaitTotal());
				nextConnectedStop.setWalkingTotal(currentNode.getWalkingTotal());
				nextConnectedStop.setFrom(currentNode);

				double weight = 0.0;
				if(!same_trip){
					weight += pp.w_change();
					weight += pp.w_moving() * Time.diffMinutes(trip_nx_st.getTime(), trip_el_st.getTime());
				}

				neighbours.putIfAbsent(nextConnectedStop, weight);
			}
		}

		// Walkable stops
		stops
			.stream()
			.filter(s->Distance.distance(s.getCoordinate(), currentNode.getElement().getCoordinate()) <= pp.walkable_radius_meters())
				.filter(s->!s.equals(currentNode.getElement()))
				.forEach(s->{
					double distance = Distance.distance(s.getCoordinate(), currentNode.getElement().getCoordinate());
					int walk_minutes = (int) Math.ceil(distance / (pp.walk_speed_mps() * 60.0));
					Time arrival_time = Time.addMinutes(currentNode.getElement().getTime(), walk_minutes);
					StopTime stoptime = new StopTime(s, arrival_time);

					stoptime.setTrip(new WalkingTrip((StopTime) currentNode.getElement(), stoptime));

					if(currentNode.getWalkingTotal() + distance > pp.max_total_walkable_distance()){
						// Walking Total exceeded!
						return;
					}

					Node<T,L> walkableStop = new Node<>((T) stoptime);
					walkableStop.setDg(this);
					walkableStop.setAlgorithm(algorithm);
					walkableStop.setChanges(currentNode.getChanges());
					walkableStop.setH(Distance.distance(s.getCoordinate(), destination));
					walkableStop.setWalkingTotal(currentNode.getWalkingTotal() + distance);
					walkableStop.setFrom(currentNode);

					double weight = 0.0;
					weight += walk_minutes * pp.w_walk();

					neighbours.put(walkableStop, weight);
				});

		return neighbours;
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
		boundingBox = boundingBox.expand(Math.max(distance*0.4, 1500));

		System.out.println("Bouding Box is: " + boundingBox.toPostGIS());

        stops = getStops(boundingBox);
        stops.forEach(e->{
            stop_by_uid.putIfAbsent(e.getUid(), e);
        });
		trips = getTrips(boundingBox);

		stop_times = getStopTimes(boundingBox);



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