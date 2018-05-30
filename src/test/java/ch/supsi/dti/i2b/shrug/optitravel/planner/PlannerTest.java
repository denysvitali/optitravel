package ch.supsi.dti.i2b.shrug.optitravel.planner;


import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.Plan;
import ch.supsi.dti.i2b.shrug.optitravel.models.Trip;
import ch.supsi.dti.i2b.shrug.optitravel.params.ComfortPlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.params.DefaultPlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.params.LongTripPreference;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlannerTest {
    @Test
    public void planSUPSI_Lamone(){
		testPlan(SUPSI_COORDINATE,
				LAMONE_FFS_COORDINATE,
				LocalDateTime.of(2018,
						5,
						17,
						16,
						30,
						0));
	}

	@Test
	public void planPregassona_LuganoCentro(){
		testPlan(PREGASSONA_COORDINATE,
				LUGANO_CENTRO_COORDINATE,
				LocalDateTime.of(2018,
						5,
						17,
						16,
						30,
						0
				));
	}

	@Test
	public void planPregassona_LuganoCentro2(){
		testPlan(PREGASSONA_COORDINATE,
				LUGANO_CENTRO_COORDINATE,
				LocalDateTime.of(2018,
						5,
						17,
						13,
						26,
						0
				),
				new ComfortPlanPreference());
	}

	@Test
	public void planLugano_Zurigo(){
		testPlan(LUGANO_COORDINATE,
				ZURICH_COORDINATE,
				LocalDateTime.of(2018,
						5,
						17,
						13,
						26,
						0
				),
				new LongTripPreference(Distance.distance(LUGANO_COORDINATE, ZURICH_COORDINATE)));
	}

	@Test
	public void planLugano_Bellinzona(){
		testPlan(LUGANO_COORDINATE,
				BELLINZONA_COORDINATE,
				LocalDateTime.of(2018,
						5,
						17,
						13,
						26,
						0
				),
				new LongTripPreference(Distance.distance(LUGANO_COORDINATE, BELLINZONA_COORDINATE)));
	}

	private void testPlan(Coordinate from, Coordinate to, LocalDateTime ldt, PlanPreference pp){
		Planner p = new Planner(
				from,
				to
		);

		p.setStartTime(ldt);
		p.setPlanPreference(pp);

		p.computePlans();
		List<Plan> plans = p.getPlans();
		assertNotEquals(null, plans);
		assertNotEquals(0, plans.size());

		Plan plan = plans.get(0);

		assertNotEquals(null, plan);
		assertNotEquals(null, plan.getTrips());

		plan.getTrips()
				.stream()
				.map(Trip::getRoute)
				.forEach(System.out::println);
	}

	private void testPlan(Coordinate from, Coordinate to, LocalDateTime ldt) {
		testPlan(from, to, ldt, new DefaultPlanPreference());
	}
}
