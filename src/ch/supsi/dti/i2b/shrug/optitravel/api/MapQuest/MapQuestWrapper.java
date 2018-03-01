package ch.supsi.dti.i2b.shrug.optitravel.api.MapQuest;

import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import javafx.scene.image.Image;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MapQuestWrapper {
    // https://developer.mapquest.com/documentation/
    private static String HOST = "open.mapquestapi.com";
    private static String CONSUMER_KEY = "pVala0FwIDGLgd5vNkfTpyyyL8sgesSW";
    private static String CONSUMER_SECRET = "vYfsTaqAL4yDRivL";

    private HttpClient client;

    public MapQuestWrapper(){
        client = new HttpClient();
    }

    public Image getMapImage(double lat, double lng, int zoom, MapType mapType){
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("staticmap/v5/map")
                .addQueryParameter("key", CONSUMER_KEY)
                .addQueryParameter("size", "500,500@2x")
                .addQueryParameter("format", "jpg")
                .addQueryParameter("center", String.format("%f,%f", lat, lng))
                .addQueryParameter("zoom", String.format("%d", zoom))
                .addQueryParameter("type", mapType.toString().toLowerCase())
                .build();
        Response response = client.get(url);
        if(response != null && response.body() != null && response.isSuccessful()) {
            try {
                File temp = File.createTempFile("optitravel_mapquest", null);
                BufferedSink sink = Okio.buffer(Okio.sink(temp));
                sink.writeAll(response.body().source());
                sink.close();
                return new Image("file:" + temp.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Image getMapImage(double lat, double lng, int zoom) throws IOException {
        return getMapImage(lat, lng, zoom, MapType.MAP);
    }

    public ArrayList<Place> getPOI(double lat, double lng){
        return new ArrayList<Place>();
    }
}
