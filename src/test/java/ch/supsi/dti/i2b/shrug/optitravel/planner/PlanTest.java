package ch.supsi.dti.i2b.shrug.optitravel.planner;

import ch.supsi.dti.i2b.shrug.optitravel.models.Plan;
import ch.supsi.dti.i2b.shrug.optitravel.models.TimedLocation;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

public class PlanTest {
	private void testPlanByClassData(String s) throws IOException, ClassNotFoundException {
		File f = new File(getClass().getClassLoader()
				.getResource(s).getFile());
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);

		List<TimedLocation> timedLocationList = (List<TimedLocation>) ois.readObject();
		Plan p = new Plan(timedLocationList);
		System.out.println(timedLocationList);
	}

	@Test
	public void testPlanImport1() throws IOException, ClassNotFoundException {
		testPlanByClassData("classdata/path-1.classdata");
	}

	@Test
	public void testPlanImport2() throws IOException, ClassNotFoundException {
		testPlanByClassData("classdata/path-2.classdata");
	}
}
