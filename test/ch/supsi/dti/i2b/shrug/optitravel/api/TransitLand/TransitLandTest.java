package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class TransitLandTest{
    private TransitLandAPIWrapper apiWrapper;

    private HttpClient client;
    public TransitLandTest(){
        apiWrapper = new TransitLandAPIWrapper();
    }

    @Test
    void map() {

        GPSCoordinates gps = new GPSCoordinates();
        TransitLandAPIWrapper mockedTRL = mock(TransitLandAPIWrapper.class);
        ArrayList<Stop> arr = new ArrayList<Stop>();

        try {

            mockedTRL.getStopsNear(gps);
            verify(mockedTRL).getStopsNear(gps);

            ArrayList<Stop> gerra = apiWrapper.getStopsNear(new GPSCoordinates(46.174372,8.911756));
            assertEquals("Gerra Piano, Paese", gerra.get(0).getName());
            assertEquals("s-u0nqdvc3me-gerrapianopaese", gerra.get(0).getId());

            mockedTRL.getRouteStopPatterns(anyString());
            verify(mockedTRL).getRouteStopPatterns(anyString());

            mockedTRL.getStopsByRoute(anyString());
            verify(mockedTRL).getStopsByRoute(anyString());

            mockedTRL.getRouteStopPatternsByStopsVisited(arr);
            verify(mockedTRL).getRouteStopPatternsByStopsVisited(arr);

            mockedTRL.getStopById(anyString());
            verify(mockedTRL).getStopById(anyString());



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