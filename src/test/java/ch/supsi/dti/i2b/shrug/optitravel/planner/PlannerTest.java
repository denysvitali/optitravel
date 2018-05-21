package ch.supsi.dti.i2b.shrug.optitravel.planner;


import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Plan;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlannerTest {
    @Test
    public void planSUPSI_Lamone(){
        Coordinate supsi = new Coordinate(46.023346, 8.917129);
        Coordinate lamone_ffs = new Coordinate(46.0396684,8.932392);
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
    }
}
