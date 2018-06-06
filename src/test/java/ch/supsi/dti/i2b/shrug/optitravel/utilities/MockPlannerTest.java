package ch.supsi.dti.i2b.shrug.optitravel.utilities;

import ch.supsi.dti.i2b.shrug.optitravel.models.Plan;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.BELLINZONA_COORDINATE;
import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.LUGANO_COORDINATE;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MockPlannerTest {
	@Test
	public void testMockPlanner(){
		MockPlanner mp = new MockPlanner(LUGANO_COORDINATE, BELLINZONA_COORDINATE);
		List<Plan> plans = mp.getPlans();
		assertNotEquals(null, plans);
	}
}
