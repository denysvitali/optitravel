package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.StopTrip;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.DropOff;
import ch.supsi.dti.i2b.shrug.optitravel.models.PickUp;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        String trip_id = "t-f492e1-bioggiomolinazzostazione";

        assertNotEquals(null, gtfSrsWrapper);
        List<Stop> stops;
        try {
             stops = gtfSrsWrapper.getStopsByTrip(trip_id);
        } catch (GTFSrsError gtfSrsError) {
            fail(gtfSrsError);
            return;
        }

        assertEquals(16, stops.size());
        for(Stop s : stops){
            assertNotEquals(null, s);
        }

        for(Stop s : stops){
            assertNotEquals(null, s.getUid());
            assertNotEquals(null, s.getName());
            assertNotEquals(null, s.getLat());
            assertNotEquals(null, s.getLng());
        }

        assertEquals("Lamone-Cadempino, Stazione", stops.get(0).getName());
        assertEquals(new Coordinate(46.04035, 8.932156), stops.get(0).getCoordinate());
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
            String uid = "a-a51548-postautoschweiz";
            Agency a = gtfSrsWrapper.getAgency(uid);
            assertEquals(uid, a.getUid());
            assertEquals("PostAuto Schweiz", a.getName());
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
            String uid = "r-3b43a2-061";
            Route r = gtfSrsWrapper.getRoute(uid);
            assertEquals(uid, r.getUID());
            assertEquals("a-cfb94d-aroserverkehrsbetriebe", r.getAgencyUID());
            assertEquals("061", r.getShortName());
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
            String uid = "t-f492e1-bioggiomolinazzostazione";
            Trip t = gtfSrsWrapper.getTrip(uid);
            assertNotEquals(null, t);
            assertEquals(uid, t.getUID());
            List<StopTrip> stopTrip = t.getStopTrip();
            assertNotEquals(null, stopTrip);
            assertNotEquals(0, stopTrip.size());

            assertEquals(DropOff.RegularlyScheduled, stopTrip.get(0).getDropOff());
            assertEquals(PickUp.RegularlyScheduled, stopTrip.get(0).getPickUp());
            assertNotEquals(null, stopTrip.get(0).getStop());

            // May change based on the feed version!
            assertEquals("s-f4b2d2-lamonecadempinostazione", stopTrip.get(0).getStop().getUid());
            assertEquals("Lamone-Cadempino, Stazione", stopTrip.get(0).getStop().getName());

            assertEquals(new Coordinate(46.04035, 8.932156), stopTrip.get(0).getStop().getCoordinate());
            assertEquals(LocalTime.of(9,35), stopTrip.get(0).getArrival());
            assertEquals(LocalTime.of(9,35), stopTrip.get(0).getDeparture());


            assertEquals(LocalTime.of(9,36), stopTrip.get(1).getArrival());
            assertEquals(LocalTime.of(9,36), stopTrip.get(1).getDeparture());
        } catch(GTFSrsError err){
            fail(err);
        }
    }

    @Test
    public void testRoutesByStop(){
        try{
            String uid = "s-e5e593-luganocentro";
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
            BoundingBox bbox = new BoundingBox(new Coordinate(46.01946,8.974125),
                    new Coordinate(46.023113,8.967738));
            List<Trip> trips = gtfSrsWrapper.getTripsByBBox(bbox);
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
}
