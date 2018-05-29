package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Result;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.StopTrip;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.AscDesc;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.sort.TripSort;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.models.PickUp;
import ch.supsi.dti.i2b.shrug.optitravel.models.StopTime;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;
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
        String trip_id = "t-0194f9-168ta207bj1811r";

        assertNotEquals(null, gtfSrsWrapper);
        List<Stop> stops;
        try {
             stops = gtfSrsWrapper.getStopsByTrip(trip_id);
        } catch (GTFSrsError gtfSrsError) {
            fail(gtfSrsError);
            return;
        }

        assertEquals(15, stops.size());
        for(Stop s : stops){
            assertNotEquals(null, s);
        }

        for(Stop s : stops){
            assertNotEquals(null, s.getUid());
            assertNotEquals(null, s.getName());
            assertNotEquals(null, s.getLat());
            assertNotEquals(null, s.getLng());
        }

        assertEquals("s-fcf74e-pregassonapiazzadigiro", stops.get(0).getUid());
        assertEquals("Pregassona, Piazza di Giro", stops.get(0).getName());
        assertEquals(new Coordinate(46.01946, 8.974125), stops.get(0).getCoordinate());
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
            assertNotEquals(r.getRouteType(), null);
        });
    }

    @Test
    public void testAgency(){
        try{
            String uid = "a-de9f39-trasportipubbliciluganesi";
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
            String uid = "r-acfc6d-7";
            Route r = gtfSrsWrapper.getRoute(uid);
            assertNotEquals(null, r);
            assertEquals(uid, r.getUID());
            assertEquals("a-de9f39-trasportipubbliciluganesi", r.getAgencyUID());
            assertEquals("7", r.getShortName());
            assertEquals("", r.getLongName());
            assertEquals("Bus", r.getDescription());
            assertEquals(RouteType.getRoute(700), r.getRouteType());
        } catch(GTFSrsError err){
            fail(err);
        }
    }

    @Test
    public void testTrip(){
        try{
            String uid = "t-0194f9-168ta207bj1811r";
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
            assertEquals("s-fcf74e-pregassonapiazzadigiro", ((StopTrip) stopTrip.get(0)).getStop().getUid());
            assertEquals("Pregassona, Piazza di Giro", stopTrip.get(0).getStop().getName());

            assertEquals(new Coordinate(46.01946, 8.974125), stopTrip.get(0).getStop().getCoordinate());
            assertEquals(new Time("15:40"), stopTrip.get(0).getArrival());
            assertEquals(new Time("15:40"), stopTrip.get(0).getDeparture());


            assertEquals(new Time("15:40"), stopTrip.get(1).getArrival());
            assertEquals(new Time("15:40"), stopTrip.get(1).getDeparture());
        } catch(GTFSrsError err){
            fail(err);
        }
    }

    @Test
    public void testRoutesByStop(){
        try{
            String uid = "s-fcf74e-pregassonapiazzadigiro";
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
			ts.per_page = 200;
			ts.offset = 1;
			ts.route = "r-acfc6d-7";
			ts.sort_by = TripSort.ArrivalTime;
			ts.sort_order = AscDesc.ASC;
			ts.departure_after = "15:00:00";

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
			assertEquals("t-061cb9-318ta207bj1812h", t.getUID());
			assertNotEquals(null, t.getRoute());
			assertEquals("Pregassona, Piazza di Giro", t.getHeadSign());
			assertEquals("se-c08245-ta-b0015", t.getServiceId());
			assertEquals(0, t.getDirectionId());
			assertEquals(t.getRoute().getUID(), "r-acfc6d-7");

			List<ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip> stop_trip = t.getStopTrip();
			assertNotEquals(null, stop_trip);

			StopTrip st = (StopTrip) stop_trip.get(0);
			assertNotEquals(null, st);

			Stop s = st.getStop();
			assertNotEquals(null, s);
			assertEquals("s-fb2366-luganocentro", s.getUid());
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
			String stop_uid = "s-c1829f-bioggiopianoni";
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
		when(s.getUid()).thenReturn("s-c1829f-bioggiopianoni");

		Time t1 = new Time("13:00:00");
		Time t2 = new Time("13:40:00");

		try{
			StopTimes stopTimes =
					gtfSrsWrapper.getStopTimesBetween(t1, t2, s);
			assertNotEquals(null, stopTimes);

			checkStopTimesContent(stopTimes);

		} catch(GTFSrsError err){
			fail(err);
		}
	}

	@Test
	public void testStopTimesBetween2(){
		Stop s = Mockito.mock(Stop.class);
		when(s.getUid()).thenReturn("s-aefcaa-mannocentrodicalcolo");

		Time t1 = new Time("16:30:00");
		Time t2 = new Time("17:26:00");

		try{
			StopTimes stopTimes =
					gtfSrsWrapper.getStopTimesBetween(t1, t2, s);
			assertNotEquals(null, stopTimes);

			checkStopTimesContent(stopTimes);

			assertEquals(4, stopTimes.getTime().size());
			TripTimeStop tts = stopTimes.getTime().get(0);
			assertEquals("s-e9f3cd-mannolamonda", tts.getNextStop());
			assertEquals(new Time("16:37:00"), tts.getTime());
			assertEquals("t-03353d-59ta20449j1815r", tts.getTrip());

		} catch(GTFSrsError err){
			fail(err);
		}
	}

	private void checkStopTimesContent(StopTimes stopTimes) {
		stopTimes.getTime().stream().forEach(e->{
			assertNotEquals(null, e);
			assertNotEquals(null, e.getNextStop());
			assertNotEquals(null, e.getTime());
			assertNotEquals(0, e.getTrip());
		});
	}
}
