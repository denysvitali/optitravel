package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.StopTrip;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Route;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Trip;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.AscDesc;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.sort.TripSort;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.models.PickUp;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.EncodingMode;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.DecodingMode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.LUGANO_BBOX;
import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.LUGANO_COORDINATE;
import static ch.supsi.dti.i2b.shrug.optitravel.common.TestingElements.SUPSI_COORDINATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GTFSrsTest {
	private GTFSrsWrapper gtfSrsWrapper;

	private static final String STOP_BIOGGIO_PIANONI = "s-7fa1f6-bioggiopianoni";
	private static final String STOP_MANNO_CENTRODICALCOLO = "s-7648b0-mannocentrodicalcolo";
	private static final String TRIP_BIOGGIO_MOLINAZZO = "t-4e4725-10ta20449j1811r";
	private static final String STOP_MANNO_LAMONDA = "s-e4263b-mannolamonda";

    public GTFSrsTest(){
        gtfSrsWrapper = new GTFSrsWrapper();
    }

    @Test
    void TestConnection(){
        try {
            assertTrue(gtfSrsWrapper.isOnline());
        } catch (GTFSrsError gtfSrsError) {
            fail(gtfSrsError);
        }
    }

    @Test
    void StopsByTripId(){
        String trip_id = TRIP_BIOGGIO_MOLINAZZO;

        assertNotEquals(null, gtfSrsWrapper);
        List<Stop> stops;
        try {
             stops = gtfSrsWrapper.getStopsByTrip(trip_id);
        } catch (GTFSrsError gtfSrsError) {
            fail(gtfSrsError);
            return;
        }

        assertEquals(12, stops.size());
        for(Stop s : stops){
            assertNotEquals(null, s);
        }

        for(Stop s : stops){
            assertNotEquals(null, s.getUid());
            assertNotEquals(null, s.getName());
            assertNotEquals(null, s.getLat());
            assertNotEquals(null, s.getLng());
        }

        assertEquals("s-0795c1-lamonecadempinostazione", stops.get(0).getUid());
        assertEquals("Lamone-Cadempino, Stazione", stops.get(0).getName());
        assertEquals(new Coordinate(46.040350,8.932156), stops.get(0).getCoordinate());
        assertEquals(0, stops.get(0).getType());
    }

    @Test
    public void testRouteType(){
        RouteType rt = RouteType.getRoute(1508);
        assertEquals(RouteType.TAXI_SERVICE, RouteType.getRoute(1508));
        assertEquals(RouteType.AIRPORT_LINK_FERRY_SERVICE, RouteType.getRoute(1012));
        assertEquals(RouteType.WATER_TRANSPORT_SERVICE, RouteType.getRouteCategory(1012));
    }

    @Test
    public void testGetRoutes(){
        List<Route> routes = null;
        try {
            routes = gtfSrsWrapper.getRoutes();
        } catch (GTFSrsError gtfSrsError) {
            fail(gtfSrsError);
        }

        if(routes == null){
            fail("List is null!");
        }

        routes.forEach((r) -> {
            assertNotEquals(r.getType(), null);
        });
    }

    @Test
    public void testAgency(){
        try{
            String uid = "a-0f7eb6-trasportipubbliciluganesi";
            Agency a = gtfSrsWrapper.getAgency(uid);
            assertEquals(uid, a.getUid());
            assertEquals("Trasporti Pubblici Luganesi", a.getName());
            assertEquals("http://www.sbb.ch/", a.getURL());
            assertEquals("Europe/Berlin", a.getTimezone());
            assertEquals("DE", a.getLang());
            assertEquals("0900 300 300 ", a.getPhone());
        } catch (GTFSrsError err){
            fail(err);
        }
    }

    @Test
    public void testRoute(){
        try{
            String uid = "r-03c93b-1";
            Route r = gtfSrsWrapper.getRoute(uid);
            assertNotEquals(null, r);
            assertEquals(uid, r.getUID());
            assertEquals("a-049862-transportforlondon", r.getAgencyUID());
            assertEquals("1", r.getShortName());
            assertEquals("", r.getLongName());
            assertEquals(null, r.getDescription());
            assertEquals(null, r.getColor());
            assertEquals(RouteType.getRoute(3), r.getType());
        } catch(GTFSrsError err){
            fail(err);
        }
    }

    @Test
    public void testTrip(){
        try{
            String uid = "t-0e6b6b-c6de6662bafc91aa3d9d3c655f70d1cc";
            Trip t = gtfSrsWrapper.getTrip(uid);
            assertNotEquals(null, t);
            assertEquals(uid, t.getUID());
            List<ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip> stopTrip = t.getStopTrip();
            assertNotEquals(null, stopTrip);
            assertNotEquals(0, stopTrip.size());

            assertEquals(DropOff.RegularlyScheduled, stopTrip.get(0).getDropOff());
            assertEquals(PickUp.RegularlyScheduled, stopTrip.get(0).getPickUp());
            assertNotEquals(null, stopTrip.get(0).getStop());

            // May change based on the feed version!
            assertEquals("s-61dbca-newoxfordstreet", ((StopTrip) stopTrip.get(0)).getStop().getUid());
            assertEquals("New Oxford Street", stopTrip.get(0).getStop().getName());

            assertEquals(new Coordinate(51.516727, 	-0.128466), stopTrip.get(0).getStop().getCoordinate());
            assertEquals(new Time("05:45"), stopTrip.get(0).getArrival());
            assertEquals(new Time("05:45"), stopTrip.get(0).getDeparture());


            assertEquals(new Time("05:47"), stopTrip.get(1).getArrival());
            assertEquals(new Time("05:47"), stopTrip.get(1).getDeparture());
        } catch(GTFSrsError err){
            fail(err);
        }
    }

    @Test
    public void testRoutesByStop(){
        try{
            String uid = "s-61dbca-newoxfordstreet";
            List<Route> routes = gtfSrsWrapper.getRouteByStop(uid);
            assertNotEquals(null, routes);
            assertNotEquals(0, routes.size());
            routes.stream().forEach(e -> System.out.println(e.getName()));
        } catch(GTFSrsError err){
            fail(err);
        }
    }

    @Test
    public void testTripsInBBox(){
        try {
            BoundingBox bbox = LUGANO_BBOX;
            PaginatedList<Trip> p_trips = gtfSrsWrapper.getTripsByBBox(bbox);
            assertNotEquals(null, p_trips);

			Meta meta = p_trips.getMeta();
			assertNotEquals(null, meta);
			assertNull(meta.error);
			assertTrue(meta.success);
			assertNotNull(meta.pagination);
			assertNotEquals(0, meta.pagination.limit);
			assertEquals(0, meta.pagination.offset);

			List<Trip> trips = p_trips.getResult();
			assertNotEquals(null, trips);

            assertNotEquals(null, trips);
            assertNotEquals(0, trips.size());
            Trip t = trips.get(0);
            // Results are inconsistent!!!
            assertNotEquals(null, t);
            /*
                assertEquals("t-9217f2-luganocentro", t.getUID());
                assertNotEquals(null, t.getRoute());
                assertEquals(t.getRoute().getUID(), "r-5f39ec-7");
            */
        } catch(GTFSrsError err){
            fail(err);
        }
    }

    @Test
	public void testTripsInBBoxQuery(){
		try {
			BoundingBox bbox = LUGANO_BBOX;
			TripSearch ts = new TripSearch();
			ts.per_page = 1000;
			ts.offset = 1;
			ts.route = "r-a498ea-7";
			ts.sort_by = TripSort.ArrivalTime;
			ts.sort_order = AscDesc.ASC;
			ts.departure_after = new Time("15:00:00");

			PaginatedList<Trip> p_trips = gtfSrsWrapper.getTripsByBBox(bbox, ts);
			assertNotEquals(null, p_trips);

			Meta meta = p_trips.getMeta();
			assertNotEquals(null, meta);
			assertNull(meta.error);
			assertTrue(meta.success);
			assertNotNull(meta.pagination);
			assertEquals((int) ts.per_page, meta.pagination.limit);
			assertEquals((int) ts.offset, meta.pagination.offset);

			List<Trip> trips = p_trips.getResult();
			assertNotEquals(null, trips);
			assertNotEquals(0, trips.size());

			Trip t = trips.get(0);
			assertNotEquals(null, t);
			assertEquals("t-d1bda2-28ta207bj1811h", t.getUID());
			assertNotEquals(null, t.getRoute());
			assertEquals("Pregassona, Piazza di Giro", t.getHeadSign());
			assertEquals("se-79b594-ta-b0bn0", t.getServiceId());
			assertEquals(0, t.getDirectionId());
			assertEquals(t.getRoute().getUID(), ts.route);

			List<ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip> stop_trip = t.getStopTrip();
			assertNotEquals(null, stop_trip);

			StopTrip st = (StopTrip) stop_trip.get(0);
			assertNotEquals(null, st);

			Stop s = st.getStop();
			assertNotEquals(null, s);
			assertEquals("s-6ad033-luganocentro", s.getUid());
			assertEquals("Lugano, Centro", s.getName());
			assertEquals(
					new Coordinate(46.00598, 8.952449),
					s.getCoordinate()
			);
			assertEquals(new Time("15:00:00"), st.getArrival());
			assertEquals(new Time("15:00:00"), st.getDeparture());
			assertEquals(1, st.getStopSequence());
			assertEquals(DropOff.RegularlyScheduled, st.getDropOff());
			assertEquals(PickUp.RegularlyScheduled, st.getPickUp());
		} catch(GTFSrsError err){
			fail(err);
		}
	}

    @Test
    public void testStopsInBBox(){
        try {
            BoundingBox bbox = new BoundingBox(new Coordinate(46.01946,8.974125),
                    new Coordinate(46.023113,8.967738));
            bbox = bbox.expand(1000);
            List<Stop> stops = gtfSrsWrapper.getStopsByBBox(bbox);
            assertNotEquals(null, stops);
            assertNotEquals(0, stops.size());
            Stop s = stops.get(0);
            // Results are inconsistent!!!
            assertNotEquals(null, s);
            assertNotEquals(null, s.getName());
            assertNotEquals(null, s.getCoordinate());
            assertNotEquals(null, s.getLat());
            assertNotEquals(null, s.getLng());
            assertNotEquals(null, s.getUid());

            for(Stop ms : stops){
				System.out.println(ms.getName() +
						" (" + ms.getCoordinate() + ")");
			}
        } catch(GTFSrsError err){
            fail(err);
        }
    }

    /*@Test
	public void testStopTimesNearCoordinate(){
    	try {
			Coordinate c = LUGANO_COORDINATE;
			double radius = 200.0;
			Time after = new Time("13:00:00");

			PaginatedList<StopTimes> st =
					gtfSrsWrapper.getStopTimes(after, c, radius);

			assertNotEquals(null, st);
			assertNotEquals(null, st.getResult());
			assertNotEquals(null, st.getMeta());
			assertNotEquals(0, st.getResult().size());
			testStopTimes(st);


		} catch(GTFSrsError err){
    		fail(err);
		}
	}*/

	@Test
	public void testStopTimesByStop(){
		try {
			String stop_uid = STOP_BIOGGIO_PIANONI;
			Time after = new Time("13:00:00");

			StopTimes st =
					gtfSrsWrapper.getStopTimesByStop(stop_uid, after);

			assertNotEquals(null, st);
			assertNotEquals(null, st.getStop());
			assertNotEquals(null, st.getTime());
			assertNotEquals(0, st.getTime().size());
			testListTripTime(st.getTime());


		} catch(GTFSrsError err){
			fail(err);
		}
	}

	private void testListTripTime(List<TripTimeStop> time) {
    	time.stream().forEach(
    			e -> {
    				assertNotEquals(null, e.getTime());
    				assertNotEquals(null, e.getTrip());
				}
		);
	}

	@Test
	public void testStopTimesNearCoordinateBetween(){
		try {
			Coordinate c = LUGANO_COORDINATE;
			double radius = 500.0;
			Time after = new Time("13:00:00");
			Time before = new Time("13:30:00");

			PaginatedList<StopTimes> st =
					gtfSrsWrapper.getStopTimesBetween(after, before, c, radius);

			assertNotEquals(null, st);
			assertNotEquals(null, st.getResult());
			assertNotEquals(null, st.getMeta());
			assertNotEquals(0, st.getResult().size());
			testStopTimes(st);


		} catch(GTFSrsError err){
			fail(err);
		}
	}

	private void testStopTimes(PaginatedList<StopTimes> st) {
		st.getResult()
				.forEach(e->{
					assertNotEquals(null, e.getStop());
					assertNotEquals(null, e.getTime());
					e.getTime().forEach(t->{
						assertNotEquals(null, t.time);
						assertNotEquals(null, t.trip);
					});
				});
	}

	@Test
	public void testStopsNear(){
		Coordinate c = SUPSI_COORDINATE;
		try {
			PaginatedList<StopDistance> stops = gtfSrsWrapper.getStopsNear(c);
			assertNotEquals(null, stops);
			List<StopDistance> result = stops.getResult();
			assertNotEquals(null, result);
			assertNotEquals(null, stops.getMeta());
			assertNotEquals(0, result.size());

			result.forEach(e->{
				assertNotEquals(null, e);
				Stop s = e.getStop();
				assertNotEquals(null, s);
				assertNotEquals(null, e.getDistance());
				assertNotEquals(null, s.getType());
				assertNotEquals(null, s.getUid());
				assertNotEquals(null, s.getName());
				assertNotEquals(null, s.getCoordinate());
			});
		} catch (GTFSrsError gtfSrsError) {
			fail(gtfSrsError);
		}
	}

	@Test
	public void testStopTimesBetween(){
		Stop s = Mockito.mock(Stop.class);
		when(s.getUid()).thenReturn(STOP_BIOGGIO_PIANONI);

		Time t1 = new Time("13:00:00");
		Time t2 = new Time("13:40:00");
		Date date = new Date("2018-05-30");

		try{
			StopTimes stopTimes =
					gtfSrsWrapper.getStopTimesBetween(t1, t2, date, s);
			assertNotEquals(null, stopTimes);

			checkStopTimesContent(stopTimes);

		} catch(GTFSrsError err){
			fail(err);
		}
	}

	@Test
	public void parsingTest1(){
		JsonIterator.setMode(DecodingMode.REFLECTION_MODE);
		JsonStream.setMode(EncodingMode.DYNAMIC_MODE);
		String text = "{\"stop\":\"s-7648b0-mannocentrodicalcolo\",\"time\":[{\"trip\":\"t-256d0c-6ta20449j1811r\",\"time\":\"17:14:00\",\"next_stop\":\"s-7fa1f6-bioggiopianoni\"},{\"trip\":\"t-48e0ce-5ta20449j1811r\",\"time\":\"16:44:00\",\"next_stop\":\"s-7fa1f6-bioggiopianoni\"},{\"trip\":\"t-5c4a41-15ta20449j1812r\",\"time\":\"16:37:00\",\"next_stop\":\"s-e4263b-mannolamonda\"},{\"trip\":\"t-9a680a-16ta20449j1812r\",\"time\":\"17:07:00\",\"next_stop\":\"s-e4263b-mannolamonda\"}]}";
		StopTimes st = JsonIterator.deserialize(text).as(StopTimes.class);
		assertNotEquals(null, st.getTime());
		assertNotEquals(null, st.getStop());
	}

	@Test
	public void testStopTimesBetween2(){
		Stop s = Mockito.mock(Stop.class);
		when(s.getUid()).thenReturn(STOP_MANNO_CENTRODICALCOLO);

		Time t1 = new Time("16:30:00");
		Time t2 = new Time("17:26:00");
		Date date = new Date("2018-05-30");

		try{
			StopTimes stopTimes =
					gtfSrsWrapper.getStopTimesBetween(t1, t2, date, s);
			assertNotEquals(null, stopTimes);

			checkStopTimesContent(stopTimes);

			assertEquals(4, stopTimes.getTime().size());
			TripTimeStop tts = stopTimes.getTime().get(0);
			assertEquals(STOP_BIOGGIO_PIANONI, tts.getNextStop());
			assertEquals(new Time("17:14:00"), tts.getTime());
			assertEquals("t-256d0c-6ta20449j1811r", tts.getTrip());

		} catch(GTFSrsError err){
			fail(err);
		}
	}

	@Test
	public void stopTimesBetweenInBBox(){
		BoundingBox bbox = LUGANO_BBOX;
		Time start_time = new Time("13:00:00");
		Time end_time = new Time("14:00:00");

		try {
			PaginatedList<StopTimes> stoptimes = gtfSrsWrapper.getStopTimesInBBoxBetween(
					bbox, start_time, end_time
			);
			assertNotEquals(null, stoptimes);
			assertNotEquals(null, stoptimes.getMeta());
			assertNotEquals(null, stoptimes.getResult());
			stoptimes.getResult().forEach(this::checkStopTimesContent);

		} catch (GTFSrsError gtfSrsError) {
			fail(gtfSrsError);
		}
	}

	private void checkStopTimesContent(StopTimes stopTimes) {
		assertNotEquals(null, stopTimes.getTime());
		stopTimes.getTime().stream().forEach(e->{
			assertNotEquals(null, e);
			assertNotEquals(null, e.getNextStop());
			assertNotEquals(null, e.getTime());
			assertNotEquals(0, e.getTrip());
		});
	}
}
