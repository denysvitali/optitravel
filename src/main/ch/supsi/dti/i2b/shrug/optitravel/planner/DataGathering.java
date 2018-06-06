package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.PaginatedList;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTimes;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.TripTimeStop;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.TransitLandAPIError;
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
	private static final double AVG_MOVING_SPEED_KMH = 50;
	private static final double AVG_MOVING_SPEED = AVG_MOVING_SPEED_KMH / (60 * 1000); // in m/minute
	private static final boolean USE_GTFS = false;
	private TransitLandAPIWrapper wTL = new TransitLandAPIWrapper();
	private GTFSrsWrapper wGTFS = new GTFSrsWrapper();
	private PubliBikeWrapper wPB = new PubliBikeWrapper();

	private List<Trip> trips = new ArrayList<>();
	private List<Stop> stops = new ArrayList<>();
	private List<StopTimes> stop_times = new ArrayList<>();
	private List<Route> routes = new ArrayList<>();

	private Time estimatedEndTime;

	private HashMap<Stop, List<TripTimeStop>> trip_time_stop_by_stop = new HashMap<>();
	private HashMap<String, Stop> stop_by_uid = new HashMap<>();
	private HashMap<StopTime, List<Trip>> tripByStopTime = new HashMap<>();

	private PlanPreference pp = new DefaultPlanPreference();

	private Date from_date;
	private Time start_time;
	private Coordinate source;
	private Coordinate destination;

	public DataGathering(){

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
			if(USE_GTFS) {
				stops.addAll(getwGTFS().getStopsByBBox(boundingBox));
			} else {
				stops.addAll(getwTL().getStopsByBBox(boundingBox));
			}
		} catch(GTFSrsError | TransitLandAPIError err){
			err.printStackTrace();
		}

		return stops;
	}

	private List<StopTimes> getStopTimes(BoundingBox boundingBox) {
		if(stop_times.size() != 0){
			return stop_times;
		}

		Time end_time = getEstimatedEndTime();
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

	public List<Trip> getTrips(BoundingBox boundingBox) {
		if (trips.size() != 0) {
			return trips;
		}

		Time end_time = getEstimatedEndTime();

		if(USE_GTFS) {
			TripSearch ts = new TripSearch();
			ts.departure_after = start_time;
			ts.arrival_before = end_time;

			PaginatedList<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip>
					gtfs_paginated_trips = null;
			try {
				gtfs_paginated_trips = getwGTFS().getTripsByBBox(boundingBox, ts);
			} catch (GTFSrsError gtfSrsError) {
				gtfSrsError.printStackTrace();
			}
			List<ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip>
					gtfs_trips = gtfs_paginated_trips.getResult();

			trips.addAll(gtfs_trips);
		} else {

			/////////////////////////////

			final List<RouteStopPattern> routeStopPatternsInBBox = new ArrayList<>();
			final List<ScheduleStopPair> scheduleStopPairsInBBox = new ArrayList<>();

			AtomicInteger count = new AtomicInteger();

			getwTL().AgetRouteStopPatternsByBBox(boundingBox, (rsps) -> {

				System.out.println(rsps.size());
				routeStopPatternsInBBox.addAll(rsps);

				synchronized (getwTL()) {
					count.getAndIncrement();
					getwTL().notify();
				}

			});

		    getwTL().AgetScheduleStopPairsByBBox(boundingBox, from_date, start_time, end_time, (ssps)->{

				System.out.println(ssps.size());
				scheduleStopPairsInBBox.addAll(ssps);
				synchronized (getwTL()) {
					count.getAndIncrement();
					getwTL().notify();
				}

			});


			synchronized (getwTL()) {
				while (count.get() != 2) {
					try {
						getwTL().wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}


			for (RouteStopPattern rsp : routeStopPatternsInBBox) {

				for (String trip_id : rsp.getTrips()) {

					List<ScheduleStopPair> schedulesInRspTrip = new ArrayList<>();
					for (ScheduleStopPair sch : scheduleStopPairsInBBox) {

						if (sch.getTrip().equals(trip_id) && sch.getRoute_stop_pattern_onestop_id().equals(rsp.getId()))
							schedulesInRspTrip.add(sch);

					}
					if (schedulesInRspTrip.size() != 0) {
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
									break;
								}
								index++;

							}

						}
						trips.add(tlTrip);
					}

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

	private Time getEstimatedEndTime() {
		if(estimatedEndTime != null){
			return estimatedEndTime;
		}

		int max_travel_minutes = 0;
		max_travel_minutes += getPlanPreference().max_total_waiting_time();
		max_travel_minutes += Math.round(Distance.distance(source, destination) * (AVG_MOVING_SPEED) + 10);
		max_travel_minutes += Math.round(pp.max_total_walkable_distance() / (pp.walk_speed_mps() * 60));
		Time end_time = Time.addMinutes(start_time, max_travel_minutes);

		estimatedEndTime = end_time;
		return estimatedEndTime;
	}

	public <T extends TimedLocation, L extends Location> HashMap<Node<T,L>, Double>
	getNeighbours(Node<T,L> currentNode, Algorithm<T,L> algorithm) {
		HashMap<Node<T,L>, Double> neighbours = new HashMap<>();

		if(tripByStopTime.size()==0){
			trips.stream().forEach(t->{
				t.getStopTrip().forEach(s->{
					StopTime st = new StopTime(s.getStop(), s.getDeparture());
					assert(st.equals(st));
					tripByStopTime.putIfAbsent(st, new ArrayList<>());
					if(!tripByStopTime.get(st).contains(t)) {
						tripByStopTime.get(st).add(t);
					}
				});
			});
		}

		if(currentNode.getElement() != null) {
			StopTime currentNodeStopTime = (StopTime) currentNode.getElement();
			// Connected stops (In a Trip)
			if(tripByStopTime.get(currentNodeStopTime) != null) {
				for (Trip t : tripByStopTime.get(currentNodeStopTime)) {

					t.setStopTrip(t.getStopTrip().stream().sorted(Comparator.comparingInt(StopTrip::getStopSequence)).collect(Collectors.toList()));
					int prev = 0;
					for(StopTrip current_stop_trip : t.getStopTrip()){
						assert (current_stop_trip.getStopSequence() > prev);
						prev = current_stop_trip.getStopSequence();
					}
					int stop_index = t.getStopIndex((Stop) currentNode.getElement().getLocation());
					if (stop_index != -1 && stop_index < t.getStopTrip().size() - 1) {
						// This stop is in the trip, and isn't the last one!
						StopTrip cr_stoptrip = t.getStopTrip().get(stop_index);
						StopTrip nx_stoptrip = t.getStopTrip().get(stop_index + 1);

						StopTime trip_el_st = new StopTime(cr_stoptrip.getStop(),
								cr_stoptrip.getDeparture());
						StopTime trip_nx_st = new StopTime(nx_stoptrip.getStop(),
								nx_stoptrip.getDeparture());

						if (!Time.isAfter(trip_el_st.getTime(), currentNode.getElement().getTime())) {
							// The starting point isn't after our current node time,
							// therefore we exclude this element
							continue;
						}

						if (!Time.isAfter(trip_nx_st.getTime(), trip_el_st.getTime())) {
							// The next stop doesn't come AFTER the current one!
							continue;
						}

						// Wait time, till the next bus / train
						double wait_time = Time.diffMinutes(trip_el_st.getTime(), currentNode.getElement().getTime());
						assert (wait_time >= 0); // Assertion, we verified this condition before.

						if (!trip_el_st.equals(currentNode.getElement())) {
							// We need to wait at this stop for X minutes,
							// before we can hop on this trip.

							if (currentNode.getWaitTotal() + wait_time > pp.max_total_waiting_time()) {
								// Waiting time exceeded
								continue;
							}

							if (wait_time > pp.max_waiting_time()) {
								// Can't wait more than the max_waiting_time
								continue;
							}

							if (!currentNode.getFrom().getElement().getLocation().equals(currentNode.getElement().getLocation())) {

								if (currentNode.getFrom() != null) {
									Node<T, L> prevNode = currentNode.getFrom();
									if (prevNode.getElement().getTrip() instanceof WaitingTrip) {
										Node<T, L> prevPrevNode = prevNode.getFrom();
										if (prevPrevNode != null) {
											if (prevPrevNode.getElement().getTrip() != null) {
												if (prevPrevNode.getElement().getTrip().getRoute().equals(t.getRoute())) {
													// Don't take the same route twice
													System.out.println("Ignoring this choice...");
													continue;
												}
											}
										}
									}
								}

								Node<T, L> nextTimedStop = new Node<>((T) trip_el_st);
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
							}
							continue;
						}

						assert (wait_time == 0);

						Node<T, L> nextConnectedStop = new Node<>((T) trip_nx_st);
						trip_nx_st.setTrip(t);
						nextConnectedStop.setAlgorithm(algorithm);
						nextConnectedStop.setDg(this);
						nextConnectedStop.setH(Distance.distance(trip_nx_st.getCoordinate(),
								destination));

						boolean same_trip = false;
						if (currentNode.getElement().getTrip() != null &&
								currentNode.getElement().getTrip().equals(t)) {
							same_trip = true;
						} else {
							if (currentNode.getChanges() + 1 > pp.max_total_changes()) {
								continue;
							}
						}

						nextConnectedStop.setChanges(currentNode.getChanges() + (same_trip ? 0 : 1));
						nextConnectedStop.setWaitTotal(currentNode.getWaitTotal());
						nextConnectedStop.setWalkingTotal(currentNode.getWalkingTotal());
						nextConnectedStop.setFrom(currentNode);

						double weight = 0.0;
						if (!same_trip) {
							weight += pp.w_change();
							weight += pp.w_moving() * Time.diffMinutes(trip_nx_st.getTime(), trip_el_st.getTime());
						}

						neighbours.putIfAbsent(nextConnectedStop, weight);
					}
				}
			} else {
				//System.out.println("No trips associated w/" + currentNodeStopTime);
			}
		}

		// Walkable stops

		for(Stop stop : stops){
			boolean connecting_stop = false;
			if(currentNode.getElement() == null){
				// It should never happen, but if it happens,
				// let's discard this element.
				continue;
			}
			if(stop.equals(currentNode.getElement().getLocation())){
				// We can't walk to the same stop!
				continue;
			}

			if(currentNode.getElement().getLocation() instanceof Stop){
				Stop s = (Stop) currentNode.getElement().getLocation();
				if(s.getParentStop() != null){
					if(s.getParentStop().equals(stop)){
						// Our Stop has the current iterated stop as a parent
						connecting_stop = true;
					}
					if(s.getParentStop().equals(stop.getParentStop())){
						// Our stop has the same parent stop as the one
						// that is currently being iterated
						connecting_stop = true;
					}
					if(stop.getParentStop().equals(s.getParentStop())){
						// The stop being iterated has our stop as a
						// parent stop
						connecting_stop = true;
					}
				} else {
					if(stop.getParentStop() != null){
						if(stop.getParentStop().equals(s)){
							// The iterated stop has our stop as a parent
							connecting_stop = true;
						}
					}
				}
			}

			double stop_distance = Distance.distance(stop.getCoordinate(), currentNode.getElement().getCoordinate());
			if(stop_distance < 100 && stop.getName().equals(((Stop) currentNode.getElement().getLocation()).getName())){
				connecting_stop = true;
			}

			if(stop_distance > pp.walkable_radius_meters()){
				// Unreachable Stop
				continue;
			}

			double distance = Distance.distance(stop.getCoordinate(), currentNode.getElement().getCoordinate());
			int walk_minutes = (int) Math.ceil(distance / (pp.walk_speed_mps() * 60.0));
			Time arrival_time = Time.addMinutes(currentNode.getElement().getTime(), walk_minutes);
			StopTime stoptime = new StopTime(stop, arrival_time);

			if(connecting_stop){
				stoptime.setTrip(new ConnectionTrip((StopTime) currentNode.getElement(), stoptime));
			} else {
				stoptime.setTrip(new WalkingTrip((StopTime) currentNode.getElement(), stoptime));
			}

			if(currentNode.getElement().getTrip() instanceof ConnectionTrip) {
				// I can't connect / walk more than once consecutively (either by Walking or Connecting)!
				continue;
			}

			if(currentNode.getElement().getTrip() instanceof WalkingTrip){
				// I can't walk more than once consecutively!
				continue;
			}


			if(currentNode.getWalkingTotal() + distance < pp.max_total_walkable_distance()){
				// Walkable Distance not exceeded, adding this node as a neighbour
				Node<T,L> walkableStop = new Node<>((T) stoptime);
				walkableStop.setDg(this);
				walkableStop.setAlgorithm(algorithm);
				walkableStop.setChanges(currentNode.getChanges());
				walkableStop.setH(Distance.distance(stop.getCoordinate(), destination));
				walkableStop.setWalkingTotal(currentNode.getWalkingTotal() + distance);
				walkableStop.setFrom(currentNode);

				double weight = 1000.0;
				weight += walk_minutes * pp.w_walk();

				neighbours.put(walkableStop, weight);
			}
		}
		return neighbours;
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

		System.out.println("Bounding Box is: " + boundingBox.toPostGIS());

		stops = getStops(boundingBox);
		stops.forEach(e->{
			stop_by_uid.putIfAbsent(e.getUid(), e);
		});
		trips = getTrips(boundingBox);

//		stop_times = getStopTimes(boundingBox);

		stop_times.forEach(e->{
			trip_time_stop_by_stop.put(
					stop_by_uid.get(
							e.getStop()),
					e.getTime()
			);
		});

	}
}