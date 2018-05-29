package ch.supsi.dti.i2b.shrug.optitravel.planner;


import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Plan;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.LAMONE_FFS_COORDINATE;
import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.SUPSI_COORDINATE;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlannerTest {
    @Test
    public void planSUPSI_Lamone(){
        Coordinate supsi = SUPSI_COORDINATE;
        Coordinate lamone_ffs = LAMONE_FFS_COORDINATE;
        Planner p = new Planner(
          supsi,
          lamone_ffs
        );

        LocalDateTime ldt = LocalDateTime.of(2018, 5, 17, 16, 30, 0);
        p.setStartTime(ldt);

        p.computePlans();
        List<Plan> plans = p.getPlans();
        assertNotEquals(null, plans);
        assertNotEquals(0, plans.size());
        assertNotEquals(null, plans.get(0));

        plans.get(0).getTrips()
				.stream()
				.map((e)->e.getRoute())
                .forEach(System.out::println);

    }

	@Test
	public void planPregassona_LuganoCentro(){
		Coordinate pregassona = new Coordinate(46.020805, 8.975589);
		Coordinate lugano = new Coordinate(46.0065117,8.9523121);
		Planner p = new Planner(
				pregassona,
				lugano
		);

		LocalDateTime ldt = LocalDateTime.of(2018, 5, 17, 16, 30, 0);
		p.setStartTime(ldt);

		p.computePlans();
		List<Plan> plans = p.getPlans();
		assertNotEquals(null, plans);
		assertNotEquals(0, plans.size());
		assertNotEquals(null, plans.get(0));

		plans.get(0).getTrips()
				.stream()
				.map((e)->e.getRoute())
				.forEach(System.out::println);

	}
}
