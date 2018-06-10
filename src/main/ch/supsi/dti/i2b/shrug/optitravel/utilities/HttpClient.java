package ch.supsi.dti.i2b.shrug.optitravel.utilities;

import ch.supsi.dti.i2b.shrug.optitravel.config.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpClient {
    private OkHttpClient client;

    public HttpClient(){
        client = new OkHttpClient();
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
    }

    private Request getRequest(HttpUrl url){
        Request r = new Request.Builder()
                .url(url)
                .header("User-Agent", "OptiTravel " + BuildConfig.getVersion())
                .get()
                .build();
        return r;
    }
/*
    private Request getRequestWithHeader(HttpUrl url, Map<String, String> headers){

        Request.Builder rb = new Request.Builder().url(url);
        for(String key : headers.keySet()){
            rb = rb.header(key, headers.get(key));
        }
        Request r = rb.get().build();
        return r;
    }

    public Response getWithHeader(HttpUrl url, Map<String, String> headers){
        try {
            Response r = client.newCall(getRequestWithHeader(url, headers)).execute();
            return r;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public Response get(HttpUrl url, long timeout){
        OkHttpClient timeoutClient = new OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();
        try {
            Response r = timeoutClient.newCall(getRequest(url)).execute();
            return r;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response get(HttpUrl url) {
        try {
            Response r = client.newCall(getRequest(url)).execute();
            return r;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void destroy() {
        if(client != null){
            System.out.println("Destroying client");
            client.connectionPool().evictAll();
            client = null;
        }
    }
}
