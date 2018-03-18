package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.config.BuildConfig;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GTFSrsWrapper {
    public static final String HOST = (BuildConfig.isDev()?"localhost":"gtfs.denv.it");
    public static final int PORT = (BuildConfig.isDev()?8000:443);
    public static final String SCHEME = (BuildConfig.isDev()?"http":"https");
    private HttpClient client;

    public GTFSrsWrapper(){
        this.client = new HttpClient();
    }

    public List<Stop> getStopsByTrip(String tid) throws GTFSrsError {
        // /api/stops/by-trip/<tid>
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME)
                .addPathSegments("api/stops/by-trip/" + tid)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
                return a.getResult().asList().stream().map(e-> e.as(Stop.class)).collect(Collectors.toList());
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new GTFSrsError("Unable to get any response for this request");
        }
    }

}
