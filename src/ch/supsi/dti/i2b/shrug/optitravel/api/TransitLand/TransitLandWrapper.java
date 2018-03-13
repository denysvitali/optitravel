package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import javafx.scene.image.Image;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;

public class TransitLandWrapper {


    private static String HOST = "transit.land";
    private HttpClient client;
    public TransitLandWrapper(){
        client = new HttpClient();
    }

    public void getNearbyStops(double lat, double lng) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("/api/v1/stops")
                .addQueryParameter("lat", String.format("%f", lat))
                .addQueryParameter("lon", String.format("%f", lng))
                .addQueryParameter("r", "400")
                .build();
        Response response = client.get(url);
        if(response != null && response.body() != null) {
            if (response.isSuccessful()) {

                System.out.println(response.body().string());

            }
            response.close();
        }
    }


}
