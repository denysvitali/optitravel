package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.params.FastPlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Algorithm;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.*;
import static org.junit.jupiter.api.Assertions.*;

public class DataGatheringTest {
	@Test
	public void testStopsGathering(){
		DataGathering dg = new DataGathering();
		List<Stop> stops = dg.getStops(LUGANO_BBOX);

		stops.forEach((s) -> {
			System.out.println(
					String.format(Locale.getDefault(), "%s - %s",
							s.getName(),
							s.getCoordinate()
					)
			);
		});

		dg.setSource(LUGANO_COORDINATE);
		dg.setDestination(BELLINZONA_COORDINATE);
		dg.setFromDate(new Date("2018-06-01"));
		dg.setStartTime(new Time("12:00:00"));

		List <Trip> trips = dg.getTrips(LUGANO_BBOX);
	}

	@Test
	public void testNeighbours(){

		try {
			DataGathering dg = new DataGathering();
			Stop stop;
			stop = dg.getwGTFS().getStop("s-89dd31-pregassonapaese");
			Time time = new Time("17:03:00");

			Node<StopTime, Stop> node = new Node<>(
					new StopTime(stop, time)
			);

			Algorithm<StopTime, Stop> algorithm = new Algorithm<>(dg);
			algorithm.setDestination(BOZZOREDA_COORDINATE);

			dg.setSource(PREGASSONA_COORDINATE);
			dg.setDestination(BOZZOREDA_COORDINATE);
			dg.setStartTime(time);
			dg.setFromDate(new Date("2018-06-01"));
			dg.setPlanPreference(new FastPlanPreference(
					Distance.distance(PREGASSONA_COORDINATE, BOZZOREDA_COORDINATE)));
			dg.fetchData();

			HashMap<Node<StopTime, Stop>,Double> neighbours =
					dg.getNeighbours(node, algorithm);

			assertNotNull(neighbours);
			assertNotEquals(0, neighbours.size());

			boolean e = false;
			for(Node n : neighbours.keySet()){
				if(n.equals(node)){
					e = true;
					System.out.println("N Equals Node");
				} else {
					System.out.println(n + " != " + node);
				}
			}

			//neighbours.keySet().forEach(System.out::println);

			assertFalse(e);


		} catch (GTFSrsError gtfSrsError) {
			fail(gtfSrsError);
		}
	}
}
