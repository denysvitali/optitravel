package ch.supsi.dti.i2b.shrug.optitravel.mock;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;
import ch.supsi.dti.i2b.shrug.optitravel.models.TimedLocation;
import ch.supsi.dti.i2b.shrug.optitravel.planner.Planner;

public class MockedPlanner<T extends TimedLocation, L extends Location> extends Planner<T,L> {
	private MockedDataGathering mockedDataGathering;
	public MockedPlanner(Coordinate from, Coordinate to) {
		super(from, to);
		this.setDG(new MockedDataGathering());
	}

	public void setMockedDG(MockedDataGathering mockedDG) {
		mockedDataGathering = mockedDG;
	}
}
