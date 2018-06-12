package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike;

import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Station;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Tariff;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.TariffModel;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.EncodingMode;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.DecodingMode;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class PubliBikeTest {

    PubliBikeWrapper pbw = new PubliBikeWrapper();

    @Test
    void deserializationTest() {
		JsonIterator.setMode(DecodingMode.STATIC_MODE);
		JsonStream.setMode(EncodingMode.STATIC_MODE);
        String s = "[{\"id\":119,\"latitude\":46.9436621,\"longitude\":7.4325071,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":142,\"latitude\":47.374242,\"longitude\":8.528573,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":94,\"latitude\":47.3758639,\"longitude\":8.5354006,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":138,\"latitude\":47.375933,\"longitude\":8.543447,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":135,\"latitude\":47.361193,\"longitude\":8.547589,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":129,\"latitude\":47.379952,\"longitude\":8.542726,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":117,\"latitude\":46.95675,\"longitude\":7.46789,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":111,\"latitude\":47.376756,\"longitude\":8.548519,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":55,\"latitude\":46.224204,\"longitude\":7.333028,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":54,\"latitude\":46.2285,\"longitude\":7.3507,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":82,\"latitude\":47.359675,\"longitude\":8.535718,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":27,\"latitude\":46.502204,\"longitude\":6.481123,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":44,\"latitude\":46.065999,\"longitude\":8.966679,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":41,\"latitude\":45.953905,\"longitude\":8.949715,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":19,\"latitude\":46.520323,\"longitude\":6.631268,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":143,\"latitude\":47.3899,\"longitude\":8.523722,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":133,\"latitude\":47.36162,\"longitude\":8.554501,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":130,\"latitude\":47.391366,\"longitude\":8.488108,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":127,\"latitude\":47.361567,\"longitude\":8.525525,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":141,\"latitude\":47.366749,\"longitude\":8.544979,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":140,\"latitude\":47.366904,\"longitude\":8.533736,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":121,\"latitude\":46.956956,\"longitude\":7.48287,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":114,\"latitude\":46.9566007,\"longitude\":7.4402185,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":106,\"latitude\":47.382798,\"longitude\":8.53545,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":137,\"latitude\":47.370869,\"longitude\":8.548045,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":134,\"latitude\":47.358557,\"longitude\":8.557088,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":132,\"latitude\":47.35594,\"longitude\":8.549852,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":105,\"latitude\":47.3775022,\"longitude\":8.5360685,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":89,\"latitude\":47.3713591,\"longitude\":8.5313344,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":88,\"latitude\":47.3651936,\"longitude\":8.5257822,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":87,\"latitude\":47.371083,\"longitude\":8.536101,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":84,\"latitude\":47.3789825,\"longitude\":8.5412505,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":59,\"latitude\":46.23055,\"longitude\":7.373705,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":28,\"latitude\":46.516228,\"longitude\":6.630028,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":100,\"latitude\":47.368064,\"longitude\":8.539046,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":14,\"latitude\":46.5224054,\"longitude\":6.5655126,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":126,\"latitude\":47.381356,\"longitude\":8.510418,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":136,\"latitude\":47.3675571,\"longitude\":8.5440615,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":128,\"latitude\":47.363148,\"longitude\":8.518143,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":37,\"latitude\":46.537813,\"longitude\":6.581462,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":7,\"latitude\":46.522591,\"longitude\":6.584961,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":10,\"latitude\":46.526079,\"longitude\":6.579268,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":21,\"latitude\":46.521043,\"longitude\":6.573253,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":6,\"latitude\":46.518108,\"longitude\":6.564945,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":80,\"latitude\":47.3795837,\"longitude\":8.5156327,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":77,\"latitude\":46.234697,\"longitude\":7.386034,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":86,\"latitude\":47.40781197,\"longitude\":8.506822854,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":102,\"latitude\":47.3897772,\"longitude\":8.5166922,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":35,\"latitude\":46.510562,\"longitude\":6.499863,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":36,\"latitude\":46.51856,\"longitude\":6.50736,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":92,\"latitude\":47.3820827,\"longitude\":8.5286468,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":104,\"latitude\":47.3739567,\"longitude\":8.5386541,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":97,\"latitude\":47.3771535,\"longitude\":8.5104883,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":50,\"latitude\":45.984764,\"longitude\":8.932378,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":45,\"latitude\":46.023159,\"longitude\":8.959919,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":22,\"latitude\":46.508486,\"longitude\":6.499667,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":33,\"latitude\":46.512991,\"longitude\":6.614122,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":11,\"latitude\":46.521978,\"longitude\":6.564896,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":49,\"latitude\":46.005653,\"longitude\":8.951857,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":58,\"latitude\":46.2278,\"longitude\":7.3595,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":25,\"latitude\":46.517157,\"longitude\":6.630652,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":98,\"latitude\":47.3731102,\"longitude\":8.5333139,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":8,\"latitude\":46.518951,\"longitude\":6.562162,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":32,\"latitude\":46.510581,\"longitude\":6.496026,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":29,\"latitude\":46.5058,\"longitude\":6.6711,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":20,\"latitude\":46.506896,\"longitude\":6.626253,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":60,\"latitude\":47.374392,\"longitude\":8.540062,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":52,\"latitude\":46.232469,\"longitude\":7.358425,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":26,\"latitude\":46.521668,\"longitude\":6.581345,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":13,\"latitude\":46.517153,\"longitude\":6.561272,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":43,\"latitude\":46.005849,\"longitude\":8.961709,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":109,\"latitude\":47.37247535,\"longitude\":8.53001018,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":34,\"latitude\":46.532879,\"longitude\":6.566487,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":38,\"latitude\":46.526373,\"longitude\":6.602609,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":110,\"latitude\":47.396401,\"longitude\":8.4860017,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":107,\"latitude\":47.3647902,\"longitude\":8.5306155,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":93,\"latitude\":47.384918,\"longitude\":8.531935,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":96,\"latitude\":47.3790787,\"longitude\":8.5308087,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":120,\"latitude\":46.9552097,\"longitude\":7.4476127,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":118,\"latitude\":46.96845,\"longitude\":7.46242,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":42,\"latitude\":45.998453,\"longitude\":8.94848,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":51,\"latitude\":45.991081,\"longitude\":8.943799,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":85,\"latitude\":47.3716661,\"longitude\":8.5235479,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":31,\"latitude\":46.50737,\"longitude\":6.495352,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":48,\"latitude\":46.0105808,\"longitude\":8.95794,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":125,\"latitude\":47.378521,\"longitude\":8.496727,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":90,\"latitude\":47.3917964,\"longitude\":8.5056978,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":62,\"latitude\":47.389862,\"longitude\":8.514605,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":131,\"latitude\":47.365119,\"longitude\":8.554114,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":61,\"latitude\":47.375991201,\"longitude\":8.541239126,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":46,\"latitude\":46.021942,\"longitude\":8.962901,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":112,\"latitude\":47.388151,\"longitude\":8.520449,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":108,\"latitude\":47.386741,\"longitude\":8.518982,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":47,\"latitude\":46.02398,\"longitude\":8.968292,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":124,\"latitude\":47.37209,\"longitude\":8.520482,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":95,\"latitude\":47.377148,\"longitude\":8.523818,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":30,\"latitude\":46.511408,\"longitude\":6.493098,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":56,\"latitude\":46.235671,\"longitude\":7.359828,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":53,\"latitude\":46.232201,\"longitude\":7.363252,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":99,\"latitude\":47.374951,\"longitude\":8.520532,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":57,\"latitude\":46.226775,\"longitude\":7.358569,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":81,\"latitude\":47.3899342,\"longitude\":8.5104051,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":101,\"latitude\":47.3625938,\"longitude\":8.5348427,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":91,\"latitude\":47.3917837,\"longitude\":8.5181701,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":40,\"latitude\":45.9242,\"longitude\":8.9192,\"state\":{\"id\":1,\"name\":\"Active\"}},{\"id\":12,\"latitude\":46.520723,\"longitude\":6.568276,\"state\":{\"id\":2,\"name\":\"Inactive\"}},{\"id\":122,\"latitude\":46.9613082,\"longitude\":7.4466679,\"state\":{\"id\":3,\"name\":\"Active (empty)\"}},{\"id\":9,\"latitude\":46.519805,\"longitude\":6.579676,\"state\":{\"id\":3,\"name\":\"Active (empty)\"}},{\"id\":83,\"latitude\":47.358663827,\"longitude\":8.520370505,\"state\":{\"id\":3,\"name\":\"Active (empty)\"}},{\"id\":23,\"latitude\":46.5175143,\"longitude\":6.5261484,\"state\":{\"id\":3,\"name\":\"Active (empty)\"}},{\"id\":18,\"latitude\":46.536889,\"longitude\":6.578087,\"state\":{\"id\":3,\"name\":\"Active (empty)\"}},{\"id\":39,\"latitude\":46.52804418,\"longitude\":6.59119606,\"state\":{\"id\":3,\"name\":\"Active (empty)\"}}]\n";
        List<Station> els = JsonIterator.deserialize(s).asList().stream()
				.map(e-> e.as(Station.class)).collect(Collectors.toList());
		System.out.println(els);
    }

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
            //stations.forEach(System.out::println);
        } catch (PubliBikeError publiBikeError) {
            fail(publiBikeError);
        }
    }

    @Test
    void getSingleStation(){
        try{
            Station s = pbw.getStation(119);
            assertNotEquals(null, s);
            assertNotEquals(null, s.getCoordinate());
            assertEquals(new Coordinate(46.9436621,7.4325071), s.getCoordinate());
            assertEquals("Hauptsitz Postauto", s.getName());
            assertEquals("Bern", s.getNetwork().getName());
        } catch(PubliBikeError e){
            fail(e);
        }
    }

    @Test
    void getTariffs(){
        try{
            List<Tariff> tariffs = pbw.getTariffs();
            assertNotEquals(0, tariffs);
            assertNotEquals(null, tariffs.get(0));
            assertEquals("QuickBike", tariffs.get(0).getName());
            assertEquals("without annual fee", tariffs.get(0).getDescription());
            assertEquals("0.-", tariffs.get(0).getBasePrice());
            assertNotEquals(null, tariffs.get(0).getTariffModels());
            for(TariffModel t : tariffs.get(0).getTariffModels()){
                assertNotEquals(null, t);
                assertNotEquals(null, t.getVehicleType());
                assertNotEquals(null, t.getFixedPrice());
                assertNotEquals(null, t.getFixedPriceDescription());
                assertNotEquals(null, t.getMaxPrice());
                assertNotEquals(null, t.getMaxPriceDescription());
                assertNotEquals(null, t.getVarPrice());
                assertNotEquals(null, t.getVarPriceDescription());
            }

        } catch(PubliBikeError e){
            fail(e);
        }
    }
}
