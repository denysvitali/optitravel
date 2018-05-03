package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike;

import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Station;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PubliBikeTest {

    PubliBikeWrapper pbw = new PubliBikeWrapper();

    @Test
    void isOnline(){
        try{
            assertTrue(pbw.isOnline());
        } catch(PubliBikeError err){
            fail(err);
        }
    }

    @Test
    void getStations(){
        List<Station> stations;
        try {
            stations = pbw.getStations();
            assertNotEquals(null, stations);
            assertNotEquals(0, stations.size());
            assertNotEquals(null, stations.get(0));
            assertNotEquals(null, stations.get(0).getCoordinate());
            assertNotEquals(null, stations.get(0).getState());
            stations.forEach(System.out::println);
        } catch (PubliBikeError publiBikeError) {
            fail(publiBikeError);
        }
    }
}
