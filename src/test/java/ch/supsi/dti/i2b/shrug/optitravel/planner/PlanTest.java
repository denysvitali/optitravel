package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.models.Plan;
import ch.supsi.dti.i2b.shrug.optitravel.models.TimedLocation;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

public class PlanTest {
	@Test
	public void testPlanImport() throws IOException, ClassNotFoundException {
		File f = new File(getClass().getClassLoader()
				.getResource("classdata/path-1.classdata").getFile());
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);

		List<TimedLocation> timedLocationList = (List<TimedLocation>) ois.readObject();
		Plan p = new Plan(timedLocationList);
		System.out.println(timedLocationList);
	}
}
