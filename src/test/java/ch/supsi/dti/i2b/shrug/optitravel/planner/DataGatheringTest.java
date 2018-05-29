package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Algorithm;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Node;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.LAMONE_FFS_COORDINATE;
import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.LUGANO_BBOX;
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

		List <Trip> trips = dg.getTrips(LUGANO_BBOX);
	}

	@Test
	public void testNeighbours(){

		try {
			DataGathering dg = new DataGathering();
			Stop stop;
			stop = dg.getwGTFS().getStop("s-e9f3cd-mannolamonda");
			Time time = new Time("16:30:00");

			Node<StopTime, Stop> node = new Node<>(
					new StopTime(stop, time)
			);

			Algorithm<StopTime, Stop> algorithm = new Algorithm<>(dg);
			algorithm.setDestination(LAMONE_FFS_COORDINATE);

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
