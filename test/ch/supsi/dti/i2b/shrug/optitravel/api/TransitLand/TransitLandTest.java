package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class TransitLandTest{
    private TransitLandAPIWrapper apiWrapper;
//    private TransitLandAPIWrapper mockedTRL = mock(TransitLandAPIWrapper.class);

    private HttpClient client;
    public TransitLandTest(){
        apiWrapper = new TransitLandAPIWrapper();
    }


    @Test
    void checkStopNear(){
        try {
/*
            mockedTRL.getStopsNear(gps);
            verify(mockedTRL).getStopsNear(gps);
*/
            List<Stop> gerra = apiWrapper.getStopsNear(new GPSCoordinates(46.174372,8.911756));
            assertEquals("Gerra Piano, Paese", gerra.get(0).getName());
            assertEquals("s-u0nqdvc3me-gerrapianopaese", gerra.get(0).getId());

        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkOrderStops(){
        Coordinate c = new Coordinate(45.485188, 9.202954);
        GPSCoordinates gpsCoordinates = new GPSCoordinates(c);
        try {
            List<Stop> stops = apiWrapper.getStopsNear(gpsCoordinates, 100);
            assertEquals(0, Distance.distance(c, stops.get(0).getCoordinates().asCoordinate()));
        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }
    }

    @Test
    void checkGetRouteStopPatterns(){
        try {
            /*
             mockedTRL.getRouteStopPatterns(anyString());
            verify(mockedTRL).getRouteStopPatterns(anyString());
             */
            List<RouteStopPattern> rsp = apiWrapper.getRouteStopPatterns("8898293");
            assertEquals("r-dr5r7-statenislandferry-b860bb-38447b", rsp.get(0).getId());
            assertEquals("r-dr5r7-statenislandferry",rsp.get(0).getRoute().getId());
        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkGetStopsByRoute(){
        try {
            /*
            mockedTRL.getStopsByRoute(anyString());
            verify(mockedTRL).getStopsByRoute(anyString());
             */
            List<Stop> stp = apiWrapper.getStopsByRoute("r-u0n7-r28");
            assertEquals("s-u0ndc04qjn-milanoportagaribaldi", stp.get(0).getId());
        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkGetRouteStopPatternsByStopsVisited(){
        try {
            /*
            mockedTRL.getRouteStopPatternsByStopsVisited(arr);
            verify(mockedTRL).getRouteStopPatternsByStopsVisited(arr);
             */

            Stop mockedStop1 = mock(Stop.class);
            when(mockedStop1.getId()).thenReturn("s-u0ndc2m6km-milanocentrale");
            Stop mockedStop2 = mock(Stop.class);
            when(mockedStop2.getId()).thenReturn("s-u0n7t3zfxx-saronno");

            List<Stop> stp = new ArrayList<>();
            stp.add(mockedStop1);
            stp.add(mockedStop2);

            List<RouteStopPattern> rsp = apiWrapper.getRouteStopPatternsByStopsVisited(stp);
            assertEquals("r-u0n7-r28-9609af-a3c09c", rsp.get(0).getId());
        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkGetStopById(){
        try {
            /*
            mockedTRL.getStopById(anyString());
            verify(mockedTRL).getStopById(anyString());
             */

            Stop stp = apiWrapper.getStopById("s-u0ndc2m6km-milanocentrale");
            assertEquals("MILANO CENTRALE", stp.getName());
        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkGetScheduleStopPairTrip(){
        try {
            /*
            mockedTRL.getScheduleStopPair(anyString());
            verify(mockedTRL).getScheduleStopPair(anyString());
             */

            assertEquals("s-u0n7t3zfxx-saronno",apiWrapper.getScheduleStopPair("8898293").get(0).getOrigin_onestop_id());

        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkGetScheduleStopPairRSP(){
        try {
            /*
            mock RouteStopPattern rsp
            mockedTRL.getScheduleStopPair(rsp);
            verify(mockedTRL).getScheduleStopPair(rsp);
             */

            RouteStopPattern mockedRSP = mock(RouteStopPattern.class);
            when(mockedRSP.getId()).thenReturn("r-u0n7-r28-9609af-a3c09c");
            assertEquals("s-u0n7t3zfxx-saronno",apiWrapper.getScheduleStopPair(mockedRSP).get(0).getOrigin_onestop_id());

        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkGetRouteStopPatternsByBBox(){
        try {
            /*
            mockedTRL.getRouteStopPatternsByBBox(gps, gps);
            verify(mockedTRL).getRouteStopPatternsByBBox(gps, gps);

             */

            List<RouteStopPattern> listRouteStopPatterns = apiWrapper.getRouteStopPatternsByBBox(new GPSCoordinates(37.668,-122.000), new GPSCoordinates(37.719,-122.500));
            assertEquals("r-9q8yy-8bx-6b7992-8e20fa", listRouteStopPatterns.get(0).getId());

        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkGetStopsByBBox(){
        try {
            /*
            mockedTRL.getStopsByBBox(gps, gps);
            verify(mockedTRL).getStopsByBBox(gps, gps);
             */

            List<Stop> listStops = apiWrapper.getStopsByBBox(new GPSCoordinates(37.668,-122.000), new GPSCoordinates(37.719,-122.500));
            assertEquals("s-9q8yt0hwpd-dalycity", listStops.get(0).getId());

        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void checkGetScheduleStopPairsByBBox(){
        try {
            /*
            mockedTRL.getScheduleStopPairsByBBox(gps, gps);
            verify(mockedTRL).getScheduleStopPairsByBBox(gps, gps);
             */

            List<ScheduleStopPair> listSch = apiWrapper.getScheduleStopPairsByBBox(new GPSCoordinates(46.197728, 8.639571), new GPSCoordinates(45.951284, 9.120199));
            assertEquals("r-u0-flixbus-4efdb3-591d18", listSch.get(0).getRoute_stop_pattern_onestop_id());

        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }

    @Test
    void SBB_Operator(){
        String sbb_id = "o-u0-sbbschweizerischebundesbahnensbb";

        assertNotEquals(null, apiWrapper);
        Operator operator = null;
        try {
            operator = apiWrapper.getOperatorById(sbb_id);
            assertNotEquals(null, operator);
        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

        assertEquals("Schweizerische Bundesbahnen", operator.getName());
        assertEquals(sbb_id, operator.getId());
        assertEquals("SBB", operator.getShortName());
        assertEquals("Europe/Berlin", operator.getTimezone());
        assertEquals("CH", operator.getCountry());
        assertNotEquals(null, operator.getGeometry());
        assertEquals("Polygon", operator.getGeometry().getType());
        try {
            assertNotEquals(null, operator.getGeometry().getPolygon());
            assertNotEquals(null,
                    operator
                            .getGeometry()
                            .getPolygon()
                            .getLines()
                            .get(0)
            );
            assertNotEquals(null,
                    operator
                            .getGeometry()
                            .getPolygon()
                            .getLines()
                            .get(0)
                            .getPoint(0)
            );
            assertEquals(45.441393, operator
                    .getGeometry()
                    .getPolygon()
                    .getLines()
                    .get(0)
                    .getPoint(0)
                    .getLatitude()
            );
            assertEquals(12.320459, operator
                    .getGeometry()
                    .getPolygon()
                    .getLines()
                    .get(0)
                    .getPoint(0)
                    .getLongitude()
            );
        } catch (TransitLandAPIError transitLandAPIError) {
            fail(transitLandAPIError);
        }

    }
}