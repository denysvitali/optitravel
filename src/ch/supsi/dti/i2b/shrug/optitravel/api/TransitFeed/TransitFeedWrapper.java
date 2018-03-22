package ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed.results.LocationsResult;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed.results.Result;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class TransitFeedWrapper {
    private static String HOST = "api.transitfeeds.com";
    private static String KEY = "05cad72f-d32f-4e9f-b3b0-66cb24be7df9";
    private HttpClient client;

    public TransitFeedWrapper(){
        this.client = new HttpClient();
    }

    public ArrayList<Location> getLocations() throws TransitFeedError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("v1/getLocations")
                .addQueryParameter("key", KEY)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                String body = response.body().string();
                Result res = JsonIterator.deserialize(body, Result.class);
                LocationsResult lres = res.getResults().as(LocationsResult.class);

                for (Location l : lres.getLocations()) {
                    if(l != null) {
                        System.out.println(l.getName());
                    }
                }
                return lres.getLocations();
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new TransitFeedError("Unable to get any response for this request");
        }
    }
}
