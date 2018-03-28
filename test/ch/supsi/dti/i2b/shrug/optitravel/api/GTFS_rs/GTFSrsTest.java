package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GTFSrsTest {
    private GTFSrsWrapper gtfSrsWrapper;

    public GTFSrsTest(){
        gtfSrsWrapper = new GTFSrsWrapper();
    }
    
    /*@Test
    void TestConnection(){
        try {
            assertEquals(true, gtfSrsWrapper.isOnline());
        } catch (GTFSrsError gtfSrsError) {
            fail(gtfSrsError);
        }
    }

    @Test
    void StopsByTripId(){
        String trip_id = "t-6ebb8e-lamone-cadempinostazione";

        assertNotEquals(null, gtfSrsWrapper);
        List<Stop> stops;
        try {
             stops = gtfSrsWrapper.getStopsByTrip(trip_id);
        } catch (GTFSrsError gtfSrsError) {
            fail(gtfSrsError);
            return;
        }

        assertEquals(9, stops.size());
        for(Stop s : stops){
            assertNotEquals(null, s);
        }

        for(Stop s : stops){
            assertNotEquals(null, s.getUid());
            assertNotEquals(null, s.getName());
            assertNotEquals(null, s.getLat());
            assertNotEquals(null, s.getLng());
        }

        assertEquals("Bioggio Molinazzo, Stazione", stops.get(0).getName());
        assertEquals(46.013046, stops.get(0).getLat());
        assertEquals(8.915061, stops.get(0).getLng());
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
    */
}