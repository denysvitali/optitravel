package ch.supsi.dti.i2b.shrug.optitravel.mock;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.PaginatedList;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.planner.DataGathering;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MockedDataGathering extends DataGathering {
	private GTFSrsWrapper mockedGTFSWrapper;
	public MockedDataGathering() {
		super();
		mockedGTFSWrapper = Mockito.mock(GTFSrsWrapper.class);
		BoundingBox bbox = new BoundingBox(
				new Coordinate(46.012963, 8.964333),
				new Coordinate(46.028526,8.976853)
		);

		TripSearch ts = Mockito.mock(TripSearch.class);
		try {
			String json_string;
			String fpath = "test-me";
			System.out.println(getClass().getResource("").getPath());
			System.out.println(getClass().getResource(fpath));
			File file = new File(getClass().getResource(fpath).getFile());
			try {
				FileInputStream fis = new FileInputStream(file);
				byte[] b = fis.readAllBytes();
				Scanner s = new Scanner(fis).useDelimiter("\\A");
				json_string = s.hasNext() ? s.next() : "";
				s.close();
				PaginatedList<Trip> cached_res_1 = mockedGTFSWrapper.parsePaginatedTrips(json_string);
				when(mockedGTFSWrapper.getTripsByBBox(any(), any())).thenReturn(cached_res_1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (GTFSrsError gtfSrsError) {
			gtfSrsError.printStackTrace();
		}
	}

	@Override
	public GTFSrsWrapper getwGTFS() {
		return mockedGTFSWrapper;
	}
}
