package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Prediction;
import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.response.PlacesAutocompleteResponse;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.Callback;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class PlacesAutocompleteWrapper {

    private static final String HOST = "maps.googleapis.com";
    private static final String SEGMENTS = "maps/api/place/autocomplete/json";
    private static final String KEY = "AIzaSyAvtzzsAPAlOrK8JbGfXfHMt18MbqCqrj4";

    public static List<Prediction> getPredictions(String s) {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .addPathSegments(SEGMENTS)
                .scheme("https")
                .addQueryParameter("input", s)
                .addQueryParameter("key", KEY)
                .build();
        HttpClient client = new HttpClient();
        Response response = client.get(url);
        return parsePredictionResponse(response);
    }

    public static void getPredictionsAsync(String s, Callback<List<Prediction>> cb) {
        Runnable r = ()->{
            cb.exec(getPredictions(s));
        };
        Thread t = new Thread(r);
        t.start();
    }

    private static List<Prediction> parsePredictionResponse(Response response) {
        if (response != null && response.isSuccessful() && response.body() != null) {
            try {
                PlacesAutocompleteResponse a = JsonIterator.deserialize(response.body().string(), PlacesAutocompleteResponse.class);
                response.close();
                return a.getPredictions();
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
