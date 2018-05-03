package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models.Station;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
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
        System.out.println(url);
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
                return a.getResult()
                        .asList()
                        .stream()
                        .map(e-> e.as(Station.class))
                        .collect(Collectors.toList());/*
                        .map(s->s.setWrapper(this))
                        .collect(Collectors.toList());*/
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
                .addPathSegments("stations")
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
                return a.getResult().as(Station.class).setWrapper(this);
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new PubliBikeError("Unable to get any response for this request");
        }
    }
}
