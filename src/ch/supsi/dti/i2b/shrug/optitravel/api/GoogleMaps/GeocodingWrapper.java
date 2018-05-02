package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Place;
import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.response.GeocodingResponse;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.Callback;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class GeocodingWrapper {

    private static final String HOST = "maps.googleapis.com";
    private static final String SEGMENTS = "maps/api/geocode/json";
    private static final String KEY = "AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4";

    public static List<Place> getPlaces(String s) {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .addPathSegments(SEGMENTS)
                .scheme("https")
                .addQueryParameter("address", s)
                .addQueryParameter("key", KEY)
                .build();
        HttpClient client = new HttpClient();
        Response response = client.get(url);
        return parsePlacesResponse(response);
    }

    public static void getPlacesAsync(String s, Callback<List<Place>> cb) {
        Runnable r = ()->{
            cb.exec(getPlaces(s));
        };
        Thread t = new Thread(r);
        t.start();
    }

    private static List<Place> parsePlacesResponse(Response response) {
        if (response != null && response.isSuccessful() && response.body() != null) {
            try {
                GeocodingResponse a = JsonIterator.deserialize(response.body().string(), GeocodingResponse.class);
                response.close();
                return a.getPlaces();
            } catch (IOException ex) {
                response.close();
                return null;
            }
        } else {
            if (response != null) {
                response.close();
            }
            return null;
        }
    }
}
