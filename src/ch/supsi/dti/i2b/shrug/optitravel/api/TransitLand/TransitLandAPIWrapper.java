package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results.RouteStopPatternsResult;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results.StopsResult;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class TransitLandAPIWrapper {
    private static String HOST = "transit.land";
    private HttpClient client;

    public TransitLandAPIWrapper(){
        this.client = new HttpClient();
    }

    public ArrayList<Stop> getStopsByRoute(String route_onestop_id) throws TransitLandAPIError {
        // https://transit.land/api/v1/stops?served_by=r-u0nmf-449
        System.out.println("getStopsByRoute");
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/stops")
                .addQueryParameter("served_by", route_onestop_id)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                StopsResult a = JsonIterator.deserialize(response.body().string(), StopsResult.class);
                return a.getStops();
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public ArrayList<Stop> getStopsNear(GPSCoordinates coordinates) throws TransitLandAPIError {
        // /api/v1/stops
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/stops")
                .addQueryParameter("lat", String.format("%f", coordinates.getLatitude()))
                .addQueryParameter("lon", String.format("%f", coordinates.getLongitude()))
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                StopsResult a = JsonIterator.deserialize(response.body().string(), StopsResult.class);
                return a.getStops();
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public ArrayList<RouteStopPattern> getRouteStopPatterns(String trip) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/route_stop_patterns")
                .addQueryParameter("trips", trip)
                .build();
        //Response response = client.get(url);
        //if(response != null && response.isSuccessful() && response.body() != null){
            try {
                RouteStopPatternsResult a = JsonIterator.deserialize(
                        "{\"route_stop_patterns\":[{\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[8.91506,46.01305],[8.91411,46.01487],[8.91795,46.01698],[8.91766,46.02005],[8.91725,46.02263],[8.91735,46.02487],[8.92179,46.03118],[8.92151,46.0337],[8.92005,46.03564],[8.92065,46.0377],[8.91853,46.03835],[8.91788,46.04114],[8.92309,46.04099],[8.92744,46.04372],[8.93215,46.04035]]},\"onestop_id\":\"r-u0nmf-449-38e351-74a711\",\"created_at\":\"2017-01-21T07:09:50.533Z\",\"updated_at\":\"2017-08-07T13:52:04.431Z\",\"tags\":{},\"created_or_updated_in_changeset_id\":7146,\"route_onestop_id\":\"r-u0nmf-449\",\"stop_pattern\":[\"s-u0nmfb7trv-bioggiomolinazzostazione\",\"s-u0nmfbg01b-bioggiocavezzolo\",\"s-u0nmfcju77-bioggioviaindustria\",\"s-u0nmfctwhw-bioggiopianoni\",\"s-u0nmffjm5p-mannocentrodicalcolo\",\"s-u0nmfft6m6-mannolamonda\",\"s-u0nmg58xh8-mannosuglio\",\"s-u0nmgh0mwp-mannomichelino\",\"s-u0nmfux3h1-mannopaese\",\"s-u0nmfuzv44-mannoscuole\",\"s-u0nmfvn2bg-mannoboschetti\",\"s-u0nmfvtc1f-gravesanoposta\",\"s-u0nmgj984y-gravesanogrumo\",\"s-u0nmgnhb06-lamoneostariettavcantonale\",\"s-u0nmgm2hqp-lamone~cadempinostazione\"],\"stop_distances\":[0.0,215.5,594.6,936.3,1225.1,1475.5,2256.2,2537.9,2782.0,3016.0,3194.7,3509.2,3911.5,4364.2,4886.7],\"geometry_source\":\"trip_stop_points\",\"color\":null,\"trips\":[\"131270\",\"131268\",\"131266\",\"131264\",\"131262\",\"131251\",\"131247\",\"131243\",\"131240\",\"131236\",\"131230\",\"131223\",\"131217\",\"131210\",\"131199\",\"131188\",\"131181\",\"131168\",\"131158\",\"131146\",\"131117\",\"131087\",\"131064\",\"131052\"]},{\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[-5.53257,50.12167],[-5.44425,50.17049],[-5.41984,50.18556],[-5.29741,50.21044],[-5.22592,50.23325],[-5.06481,50.26384],[-4.78936,50.33952],[-4.70468,50.35533],[-4.66597,50.40718],[-4.66293,50.44586],[-4.46958,50.44686],[-4.20911,50.40736],[-4.18681,50.40139],[-4.17071,50.37853],[-4.14332,50.37783]]},\"onestop_id\":\"r-gcn-greatmalverntolondonpaddingtongw-920b95-2a861b\",\"created_at\":\"2017-07-04T23:44:43.040Z\",\"updated_at\":\"2017-08-01T05:19:14.523Z\",\"tags\":null,\"created_or_updated_in_changeset_id\":7108,\"route_onestop_id\":\"r-gcn-greatmalverntolondonpaddingtongw\",\"stop_pattern\":[\"s-gbuj4h77cu-penzance\\u003c9100penznce\",\"s-gbujkj787c-sterth\\u003c9100sterth\",\"s-gbujs8j2hd-hayle\\u003c9100hayle\",\"s-gbujxkw912-camborne\\u003c9100cborne\",\"s-gbumc0dtgh-redruth\\u003c9100redruth\",\"s-gbumuwrbj6-truro\\u003c9100truro\",\"s-gbuw7h02zy-staustell\\u003c9100staustl\",\"s-gbuwkyyjj1-par\\u003c9100parr\",\"s-gbuwvb6755-lostwithiel\\u003c9100lstwthl\",\"s-gbuwvzks3v-bodminparkway\\u003c9100bodmnpw\",\"s-gbuyfr9670-liskeard\\u003c9100liskard\",\"s-gbvnb0rh0s-saltash\\u003c9100sash\",\"s-gbvn8xr32k-stbudeauxferryroad\\u003c9100stbdxfr\",\"s-gbvn954upq-devonport\\u003c9100devnprt\",\"s-gbvn9enbjs-plymouth\\u003c9100plymth\"],\"stop_distances\":[0.0,8320.5,10737.7,19891.1,25580.9,37544.2,58865.1,65132.6,71525.2,75836.5,89543.2,108531.1,110247.2,113036.7,114982.7],\"geometry_source\":\"trip_stop_points\",\"color\":null,\"trips\":[\"131270\",\"13981\"]}],\"meta\":{\"sort_key\":\"id\",\"sort_order\":\"asc\",\"per_page\":50,\"offset\":0}}", RouteStopPatternsResult.class
                );
                return a.getRouteStopPatterns();
            } catch(Exception ex){
                return null;
            }
        //} else {
        //    throw new TransitLandAPIError("Unable to get any response for this request");
        //}
    }
}
