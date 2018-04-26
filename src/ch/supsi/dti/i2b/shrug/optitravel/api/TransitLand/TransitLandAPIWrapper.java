package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results.*;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TransitLandAPIWrapper {
    private static String HOST = "api.transit.land";
    private static int PER_PAGE = 200;
    private static int MAX_REQUESTS = 1000/PER_PAGE;
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
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        StopsResult stopsResult;
        List<Stop> listStop = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    stopsResult = JsonIterator.deserialize(str, StopsResult.class);
                    response.close();
                    listStop.addAll(stopsResult.getStops());

                } catch (IOException ex) {
                    response.close();
                    return listStop;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(stopsResult.getMeta().getNext() != null)
                url = HttpUrl.parse(stopsResult.getMeta().getNext());

            maxRequestLimit--;
        }while(stopsResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listStop;

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
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        StopsResult stopsResult;
        List<Stop> listStop = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    stopsResult = JsonIterator.deserialize(str, StopsResult.class);
                    response.close();
                    listStop.addAll(stopsResult.getStops());

                } catch (IOException ex) {
                    response.close();
                    return sortStops(coordinates, listStop);
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(stopsResult.getMeta().getNext() != null)
                url = HttpUrl.parse(stopsResult.getMeta().getNext());

            maxRequestLimit--;
        }while(stopsResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return sortStops(coordinates, listStop);
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
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        StopsResult stopsResult;
        List<Stop> listStop = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    stopsResult = JsonIterator.deserialize(str, StopsResult.class);
                    response.close();
                    listStop.addAll(stopsResult.getStops());

                } catch (IOException ex) {
                    response.close();
                    return sortStops(coordinates, listStop);
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(stopsResult.getMeta().getNext() != null)
                url = HttpUrl.parse(stopsResult.getMeta().getNext());

            maxRequestLimit--;
        }while(stopsResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return sortStops(coordinates, listStop);
    }

    public List<RouteStopPattern> getRouteStopPatterns(String trip) throws TransitLandAPIError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/route_stop_patterns")
                .addQueryParameter("trip", trip)
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        RouteStopPatternsResult rspResult;
        List<RouteStopPattern> listRsp = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    rspResult = JsonIterator.deserialize(str, RouteStopPatternsResult.class);
                    response.close();
                    listRsp.addAll(rspResult.getRouteStopPatterns());

                } catch (IOException ex) {
                    response.close();
                    return listRsp;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(rspResult.getMeta().getNext() != null)
                url = HttpUrl.parse(rspResult.getMeta().getNext());

            maxRequestLimit--;
        }while(rspResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listRsp;
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
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        ScheduleStopPairResult sspResult;
        List<ScheduleStopPair> listSch = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    sspResult = JsonIterator.deserialize(str, ScheduleStopPairResult.class);
                    response.close();
                    listSch.addAll(sspResult.getScheduleStopPairs());

                } catch (IOException ex) {
                    response.close();
                    return listSch;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(sspResult.getMeta().getNext() != null)
                url = HttpUrl.parse(sspResult.getMeta().getNext());

            maxRequestLimit--;
        }while(sspResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listSch;
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
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        ScheduleStopPairResult sspResult;
        List<ScheduleStopPair> listSch = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    sspResult = JsonIterator.deserialize(str, ScheduleStopPairResult.class);
                    response.close();
                    listSch.addAll(sspResult.getScheduleStopPairs());

                } catch (IOException ex) {
                    response.close();
                    return listSch;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(sspResult.getMeta().getNext() != null)
                url = HttpUrl.parse(sspResult.getMeta().getNext());

            maxRequestLimit--;
        }while(sspResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listSch;
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
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        ScheduleStopPairResult sspResult;
        List<ScheduleStopPair> listSch = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    sspResult = JsonIterator.deserialize(str, ScheduleStopPairResult.class);
                    response.close();
                    listSch.addAll(sspResult.getScheduleStopPairs());

                } catch (IOException ex) {
                    response.close();
                    return listSch;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(sspResult.getMeta().getNext() != null)
                url = HttpUrl.parse(sspResult.getMeta().getNext());

            maxRequestLimit--;
        }while(sspResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listSch;
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
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        ScheduleStopPairResult sspResult;
        List<ScheduleStopPair> listSch = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    sspResult = JsonIterator.deserialize(str, ScheduleStopPairResult.class);
                    response.close();
                    listSch.addAll(sspResult.getScheduleStopPairs());

                } catch (IOException ex) {
                    response.close();
                    return listSch;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(sspResult.getMeta().getNext() != null)
                url = HttpUrl.parse(sspResult.getMeta().getNext());

            maxRequestLimit--;
        }while(sspResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listSch;
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
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();
        Response response;
        RouteStopPatternsResult rspResult;
        List<RouteStopPattern> listRsp = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    rspResult = JsonIterator.deserialize(str, RouteStopPatternsResult.class);
                    response.close();
                    listRsp.addAll(rspResult.getRouteStopPatterns());

                } catch (IOException ex) {
                    response.close();
                    return listRsp;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(rspResult.getMeta().getNext() != null)
                url = HttpUrl.parse(rspResult.getMeta().getNext());

            maxRequestLimit--;
        }while(rspResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listRsp;
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
                String str = response.body().string();
                byte ptext[] = str.getBytes("ISO-8859-1");
                str = new String(ptext, "UTF-8");
                operator = JsonIterator.deserialize(str, OperatorsResult.class)
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
                String str = response.body().string();
                byte ptext[] = str.getBytes("ISO-8859-1");
                str = new String(ptext, "UTF-8");
                stop = JsonIterator.deserialize(str, StopsResult.class)
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

    public List<RouteStopPattern> getRouteStopPatternsByBBox(GPSCoordinates coord1, GPSCoordinates coord2) throws TransitLandAPIError {

        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/route_stop_patterns")
                .addQueryParameter("bbox", coord1.getLongitude()+","+coord1.getLatitude()+","+coord2.getLongitude()+","+coord2.getLatitude())
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();

        Response response;
        RouteStopPatternsResult rspResult;
        List<RouteStopPattern> listRsp = new ArrayList<>();

        int maxRequestLimit = MAX_REQUESTS;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    rspResult = JsonIterator.deserialize(str, RouteStopPatternsResult.class);
                    response.close();
                    listRsp.addAll(rspResult.getRouteStopPatterns());

                } catch (IOException ex) {
                    response.close();
                    return listRsp;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(rspResult.getMeta().getNext() != null)
                url = HttpUrl.parse(rspResult.getMeta().getNext());

            maxRequestLimit--;
        }while(rspResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listRsp;
    }

    public void AgetRouteStopPatternsByBBox(GPSCoordinates coord1, GPSCoordinates coord2, Callback<List<RouteStopPattern>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getRouteStopPatternsByBBox(coord1, coord2));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }


    public List<Stop> getStopsByBBox(GPSCoordinates coord1, GPSCoordinates coord2) throws TransitLandAPIError {

        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .scheme("https")
                .addPathSegments("api/v1/stops")
                .addQueryParameter("bbox", coord1.getLongitude()+","+coord1.getLatitude()+","+coord2.getLongitude()+","+coord2.getLatitude())
                .addQueryParameter("per_page", String.valueOf(PER_PAGE))
                .build();

        Response response;
        StopsResult stopsResult;
        List<Stop> listStop = new ArrayList<>();

        int maxRequestLimit = 20;
        do{
            response = client.get(url,(long) 50E3);

            if (response != null && response.isSuccessful() && response.body() != null) {
                try {
                    String str = response.body().string();
                    byte ptext[] = str.getBytes("ISO-8859-1");
                    str = new String(ptext, "UTF-8");
                    stopsResult = JsonIterator.deserialize(str, StopsResult.class);
                    response.close();
                    listStop.addAll(stopsResult.getStops());

                } catch (IOException ex) {
                    response.close();
                    return listStop;
                }
            } else {
                if (response != null)
                    response.close();
                throw new TransitLandAPIError("Unable to get any response for this request");
            }

            if(stopsResult.getMeta().getNext() != null)
                url = HttpUrl.parse(stopsResult.getMeta().getNext());

            maxRequestLimit--;
        }while(stopsResult.getMeta().getNext() != null && maxRequestLimit != 0);

        response.close();
        return listStop;
    }

    public void AgetStopsByBBox(GPSCoordinates coord1, GPSCoordinates coord2, Callback<List<Stop>> cb){
        Runnable r = ()->{
            try {
                cb.exec(getStopsByBBox(coord1, coord2));
            } catch (TransitLandAPIError transitLandAPIError) {
                transitLandAPIError.printStackTrace();
                cb.exec(null);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public List<Stop> sortStops(GPSCoordinates coordinates, List<Stop> arr){
        Coordinate c = coordinates.asCoordinate();
        if(arr == null){
            return new ArrayList<Stop>();
        }
        /*
        Map<Double, Stop> map = new SortedMap<Double, Stop>() {
        };
        for(Stop stop : arr){
            Double distance = Distance.distance(c, stop.getCoordinates().asCoordinate());
            map.put()
        }*/
        return arr.stream().sorted((s1, s2) -> {
            double d1 = Distance.distance(c, s1.getCoordinates().asCoordinate());
            double d2 = Distance.distance(c, s2.getCoordinates().asCoordinate());

            if(d1 < d2){
                return -1;
            }
            if(d1 > d2){
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
