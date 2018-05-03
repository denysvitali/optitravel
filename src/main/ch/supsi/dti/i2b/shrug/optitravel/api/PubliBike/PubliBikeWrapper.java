package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike;

import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Station;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Tariff;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PubliBikeWrapper {
    public static final String HOST = "api.publibike.ch";
    public static final String ENDPOINT = "v1/public";
    public static final String SCHEME = "https";
    private HttpClient client;

    public PubliBikeWrapper(){
        this.client = new HttpClient();
    }
    
    public boolean isOnline() throws PubliBikeError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme(SCHEME)
                .addPathSegments(ENDPOINT)
                .addPathSegments("stations")
                .build();
        System.out.println(url);
        Response response = client.get(url);
        if(response != null && response.isSuccessful()){
                return response.isSuccessful();
        } else {
            throw new PubliBikeError("Unable to get any response for this request");
        }
    }

    public List<Station> getStations() throws PubliBikeError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme(SCHEME)
                .addPathSegments(ENDPOINT)
                .addPathSegments("stations")
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                String body = response.body().string();
                return JsonIterator.deserialize(body).asList().stream()
                        .map(s -> s.as(Station.class))
                        .map(s-> s.setWrapper(this))
                        .collect(Collectors.toList());
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new PubliBikeError("Unable to get any response for this request");
        }
    }

    public Station getStation(int id) throws PubliBikeError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme(SCHEME)
                .addPathSegments(ENDPOINT)
                .addPathSegments("stations")
                .addPathSegment(String.valueOf(id))
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                Station a = JsonIterator.deserialize(response.body().string(), Station.class);
                return a.setWrapper(this);
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new PubliBikeError("Unable to get any response for this request");
        }
    }

    public List<Tariff> getTariffs() throws PubliBikeError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme(SCHEME)
                .addPathSegments(ENDPOINT)
                .addPathSegments("tariffs")
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                List<Any> a = JsonIterator.deserialize(response.body().string()).asList();
                return a.stream().map(e->e.as(Tariff.class)).collect(Collectors.toList());
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new PubliBikeError("Unable to get any response for this request");
        }
    }
}
