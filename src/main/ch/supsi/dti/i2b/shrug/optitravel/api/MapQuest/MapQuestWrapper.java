package ch.supsi.dti.i2b.shrug.optitravel.api.MapQuest;

import ch.supsi.dti.i2b.shrug.optitravel.api.MapQuest.exceptions.MapQuestException;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import javafx.scene.image.Image;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public Image getMapImage(double lat, double lng, int zoom, MapType mapType) throws MapQuestException {

        String imageSize = "500,500@2x";
        String format = "jpg";
        String center = String.format("%f,%f", lat, lng);
        String mapZoom = String.format("%d", zoom);
        String type = mapType.toString().toLowerCase();
        String hash = "";

        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder();
            sb.append(imageSize);
            sb.append(format);
            sb.append(center);
            sb.append(mapZoom);
            sb.append(type);
            byte[] result = mDigest.digest(sb.toString().getBytes());

            StringBuilder sb2 = new StringBuilder();
            for (byte aResult : result) {
                sb2.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb2.toString();
            System.out.println(String.format("Hash: %s", hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String tempDir = System.getProperty("java.io.tmpdir");
        if(tempDir.equals("")){
            throw new MapQuestException("java.io.tmpdir not set!");
        }
        String mapsCacheDir = tempDir + "/optitravel/maps_cache";
        File f = new File(mapsCacheDir);
        if(!f.exists()) {
            if (!(f.mkdirs())) {
                throw new MapQuestException("Cannot create temp dir");
            }
        }

        File mapfile = new File(mapsCacheDir + "/" + hash);
        if(!hash.equals("")) {
            if(mapfile.exists()){
                return new Image("file:" + mapfile.getAbsolutePath());
            }
        }

        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("staticmap/v5/map")
                .addQueryParameter("key", CONSUMER_KEY)
                .addQueryParameter("size", imageSize)
                .addQueryParameter("format", format)
                .addQueryParameter("center", center)
                .addQueryParameter("zoom", mapZoom)
                .addQueryParameter("type", type)
                .build();
        Response response = client.get(url);
        if(response != null && response.body() != null && response.isSuccessful()) {
            try {
                BufferedSink sink = Okio.buffer(Okio.sink(mapfile));
                sink.writeAll(response.body().source());
                sink.close();
                return new Image("file:" + mapfile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Image getMapImage(double lat, double lng, int zoom) throws IOException, MapQuestException {
        return getMapImage(lat, lng, zoom, MapType.MAP);
    }

    public ArrayList<Place> getPOI(double lat, double lng){
        return new ArrayList<Place>();
    }
}
