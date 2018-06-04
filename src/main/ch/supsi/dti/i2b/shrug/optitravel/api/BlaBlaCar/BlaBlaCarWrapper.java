package ch.supsi.dti.i2b.shrug.optitravel.api.BlaBlaCar;



import ch.supsi.dti.i2b.shrug.optitravel.api.BlaBlaCar.models.BlaBlaTrip;
import ch.supsi.dti.i2b.shrug.optitravel.api.BlaBlaCar.results.BlaBlaTripsResult;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BlaBlaCarWrapper {
    private final static String KEY = "215d6f21698f4489811d102a5fb86da5";
    private static String HOST = "public-api.blablacar.com";
    private static int PER_PAGE = 200;
    private static int MAX_REQUESTS = 100000/PER_PAGE;
    private HttpClient client;

    public BlaBlaCarWrapper(){
        this.client = new HttpClient();
    }

    public List<BlaBlaTrip> getBlaBlaTrips(Coordinate coord1, Coordinate coord2){
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v2/trips")
                .addQueryParameter("locale", "en_GB")
                .addQueryParameter("_format", "json")
                .addQueryParameter("cur", "EUR")
                .addQueryParameter("fc", coord1.getLat()+"|"+coord1.getLng())
                .addQueryParameter("tc", coord2.getLat()+"|"+coord2.getLng())
                .addQueryParameter("db", "2018-05-30")
                .addQueryParameter("limit", "50")
                .addQueryParameter("key", KEY)
                .build();

        Response response;
        BlaBlaTripsResult tripResult;
        List<BlaBlaTrip> listTrip = new ArrayList<>();

        response = client.get(url,(long) 50E3);

        if (response != null && response.isSuccessful() && response.body() != null) {
            try {
                if(response != null) {
                    if(response.body() != null) {
                        String str = response.body().string();
                        byte ptext[] = str.getBytes("ISO-8859-1");
                        str = new String(ptext, "UTF-8");
                        tripResult = JsonIterator.deserialize(str, BlaBlaTripsResult.class);
                        listTrip.addAll(tripResult.getTrips());
                    }
                    response.close();
                }
                return listTrip;
            } catch (IOException ex) {
                response.close();
                return new ArrayList<>();
            }
        } else {
            if (response != null)
                response.close();
            return new ArrayList<>();

        }
    }
    public void destroy() {
        if(client != null){
            client.destroy();
        }
    }
}
