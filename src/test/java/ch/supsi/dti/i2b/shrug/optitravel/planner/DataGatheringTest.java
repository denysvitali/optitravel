package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.models.Trip;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.LUGANO_BBOX;

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
}
