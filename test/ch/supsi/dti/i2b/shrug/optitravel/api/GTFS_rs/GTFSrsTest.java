package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;
import org.junit.jupiter.api.Test;

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
}