package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Operator;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results.*;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransitLandAPIWrapper {
    private static String HOST = "api.transit.land";
    private HttpClient client;

    public TransitLandAPIWrapper(){
        this.client = new HttpClient();
    }

    public List<Stop> getStopsByRoute(String route_onestop_id) throws TransitLandAPIError {
        // https://transit.land/api/v1/stops?served_by=r-u0nmf-449
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/stops")
                .addQueryParameter("served_by", route_onestop_id)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                StopsResult a = JsonIterator.deserialize(response.body().string(), StopsResult.class);
                return a.getStops();
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new TransitLandAPIError("Unable to get any response for this request");
        }

    }

    public void AgetStopsByRoute(String route_onestop_id, Callback<List<Stop>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getStopsByRoute(route_onestop_id));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public List<Stop> getStopsNear(GPSCoordinates coordinates) throws TransitLandAPIError {
        // /api/v1/stops
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/stops")
                .addQueryParameter("lat", String.format("%f", coordinates.getLatitude()))
                .addQueryParameter("lon", String.format("%f", coordinates.getLongitude()))
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                StopsResult a = JsonIterator.deserialize(response.body().string(), StopsResult.class);
                response.close();
                return sortStops(coordinates, a.getStops());
            } catch(IOException ex){
                response.close();
                return null;
            }
        } else {
            if(response!=null)
                response.close();
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public List<Stop> getStopsNear(GPSCoordinates coordinates, double meters) throws TransitLandAPIError {
        // /api/v1/stops
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/stops")
                .addQueryParameter("lat", String.format("%f", coordinates.getLatitude()))
                .addQueryParameter("lon", String.format("%f", coordinates.getLongitude()))
                .addQueryParameter("r", String.format("%f", meters))
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                StopsResult a = JsonIterator.deserialize(response.body().string(), StopsResult.class);
                response.close();
                return sortStops(coordinates, a.getStops());
            } catch(IOException ex){
                response.close();
                return null;
            }
        } else {
            if(response!=null)
                response.close();
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public List<RouteStopPattern> getRouteStopPatterns(String trip) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/route_stop_patterns")
                .addQueryParameter("trip", trip)
                .build();
        Response response = client.get(url, 20*1000);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                RouteStopPatternsResult a = JsonIterator.deserialize(response.body().string(), RouteStopPatternsResult.class);
                response.close();
                return a.getRouteStopPatterns();
            } catch(IOException ex){
                response.close();
                return null;
            }
        } else {
            if(response!=null)
                response.close();
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }



    public void AgetRouteStopPatterns(String trip, Callback<List<RouteStopPattern>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getRouteStopPatterns(trip));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public List<ScheduleStopPair> getScheduleStopPair(String trip) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/schedule_stop_pairs")
                .addQueryParameter("trip", trip)
                .build();
        Response response = client.get(url, 20*1000);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ScheduleStopPairResult a = JsonIterator.deserialize(response.body().string(), ScheduleStopPairResult.class);
                response.close();
                return a.getScheduleStopPairs();
            } catch(IOException ex){
                response.close();
                return null;
            }
        } else {
            if(response!=null)
                response.close();
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public void AgetScheduleStopPair(String trip, Callback<List<ScheduleStopPair>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getScheduleStopPair(trip));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public List<ScheduleStopPair> getScheduleStopPair(RouteStopPattern rsp) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/schedule_stop_pairs")
                .addQueryParameter("route_stop_pattern_onestop_id", rsp.getId())
                .build();
        Response response = client.get(url, 20*1000);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ScheduleStopPairResult a = JsonIterator.deserialize(response.body().string(), ScheduleStopPairResult.class);
                response.close();
                return a.getScheduleStopPairs();
            } catch(IOException ex){
                response.close();
                return null;
            }
        } else {
            if(response!=null)
                response.close();
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public void AgetScheduleStopPair(RouteStopPattern rsp, Callback<List<ScheduleStopPair>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getScheduleStopPair(rsp));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public List<ScheduleStopPair> getScheduleStopPair(RouteStopPattern rsp, int day, int month, int year) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/schedule_stop_pairs")
                .addQueryParameter("route_stop_pattern_onestop_id", rsp.getId())
                .addQueryParameter("date", year+"-"+month+"-"+day)
                .build();
        Response response = client.get(url, 20*1000);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ScheduleStopPairResult a = JsonIterator.deserialize(response.body().string(), ScheduleStopPairResult.class);
                response.close();
                return a.getScheduleStopPairs();
            } catch(IOException ex){
                response.close();
                return null;
            }
        } else {
            if(response!=null)
                response.close();
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public void AgetScheduleStopPair(RouteStopPattern rsp, int day, int month, int year, Callback<List<ScheduleStopPair>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getScheduleStopPair(rsp, day, month, year));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public List<ScheduleStopPair> getScheduleStopPair(RouteStopPattern rsp, int day, int month, int year, String betweenI, String betweenF) throws TransitLandAPIError {

        //between format:   "07:00:00"

        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/schedule_stop_pairs")
                .addQueryParameter("route_stop_pattern_onestop_id", rsp.getId())
                .addQueryParameter("date", year+"-"+month+"-"+day)
                .addQueryParameter("origin_departure_between", betweenI+","+betweenF)

                .build();
        Response response = client.get(url, 20*1000);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ScheduleStopPairResult a = JsonIterator.deserialize(response.body().string(), ScheduleStopPairResult.class);
                response.close();
                return a.getScheduleStopPairs();
            } catch(IOException ex){
                response.close();
                return null;
            }
        } else {
            if(response!=null)
                response.close();
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public void AgetScheduleStopPair(RouteStopPattern rsp, int day, int month, int year, String betweenI, String betweenF, Callback<List<ScheduleStopPair>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getScheduleStopPair(rsp, day, month, year, betweenI, betweenF));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public List<RouteStopPattern> getRouteStopPatternsByStopsVisited(List<Stop> stops) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/route_stop_patterns")
                .addQueryParameter("stops_visited",
                        stops.stream()
                        .map(Stop::getId)
                        .reduce((t,u)->t + "," + u)
                        .get()
                )
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                RouteStopPatternsResult a = JsonIterator.deserialize(response.body().string(),
                        RouteStopPatternsResult.class);
                response.close();
                return a.getRouteStopPatterns();
            } catch(IOException ex){
                response.close();
                return null;
            }
        } else {
            if(response!=null)
                response.close();
            throw new TransitLandAPIError("Unable to get any response for this request");
        }
    }

    public void AgetRouteStopPatternsByStopsVisited(ArrayList<Stop> stops, Callback<List<RouteStopPattern>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getRouteStopPatternsByStopsVisited(stops));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public Operator getOperatorById(String oid) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/operators")
                .addQueryParameter("onestop_id", oid)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            Operator operator = null;
            try {
                operator = JsonIterator.deserialize(response.body().string(), OperatorsResult.class)
                        .getOperators()
                        .get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.close();
            return operator;
        }
        if(response!=null)
            response.close();
        throw new TransitLandAPIError("Unable to get any response for this request");
    }

    public Stop getStopById(String oid) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/stops")
                .addQueryParameter("onestop_id", oid)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            Stop stop = null;
            try {
                stop = JsonIterator.deserialize(response.body().string(), StopsResult.class)
                        .getStops()
                        .get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.close();
            return stop;
        }
        if(response!=null)
            response.close();
        throw new TransitLandAPIError("Unable to get any response for this request");
    }

    public List<Stop> sortStops(GPSCoordinates coordinates, List<Stop> arr){
        Coordinate c = coordinates.asCoordinate();
        return arr.stream().sorted((s1, s2) -> {
            double d1 = Distance.distance(c, s1.getCoordinates().asCoordinate());
            double d2 = Distance.distance(c, s2.getCoordinates().asCoordinate());

            if(d1 < d2){
                return -1;
            }
            if(d2 > d1){
                return 1;
            }
            return 0;
        }).collect(Collectors.toList());
    }

    public void destroy() {
        if(client != null){
            client.destroy();
        }
    }
}
