package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

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
}
