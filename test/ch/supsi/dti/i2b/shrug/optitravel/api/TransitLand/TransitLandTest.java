package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransitLandTest{
    private TransitLandAPIWrapper apiWrapper;

    public TransitLandTest(){
        apiWrapper = new TransitLandAPIWrapper();
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