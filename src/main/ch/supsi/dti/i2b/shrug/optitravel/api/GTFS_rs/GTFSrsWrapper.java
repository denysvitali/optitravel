package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import ca.fuzzlesoft.JsonParse;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Error;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Meta;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Result;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.models.Pagination;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.config.BuildConfig;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Date;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.EncodingMode;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.DecodingMode;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GTFSrsWrapper {
    public static final String HOST = (BuildConfig.isDev() && !BuildConfig.USE_GTFS_REMOTE ? "192.168.43.34": "gtfs.ded1.denv.it");
    public static final int PORT = (BuildConfig.isDev() && !BuildConfig.USE_GTFS_REMOTE ? 8000 : 443);
    public static final String SCHEME = (BuildConfig.isDev() && !BuildConfig.USE_GTFS_REMOTE ? "http" : "https");
    private HttpClient client;

    public GTFSrsWrapper(){
        this.client = new HttpClient();
		restoreJsonIteratorMode();

        /*JsonIterator.setMode(DecodingMode.STATIC_MODE);
		JsonStream.setMode(EncodingMode.STATIC_MODE);*/
    }
    
    public boolean isOnline() throws GTFSrsError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME)
                .addPathSegments("api/")
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
                boolean online = response.isSuccessful();
                response.close();
                return online;
        } else {
            throw new GTFSrsError("Unable to get any response for this request");
        }
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
        return parseStopResponse(response);
    }

	private List<Stop> parseStopResponse(Response response) throws GTFSrsError {
		if(response != null && response.isSuccessful() && response.body() != null){
			try {
				JsonIterator.setMode(DecodingMode.REFLECTION_MODE);
				JsonStream.setMode(EncodingMode.REFLECTION_MODE);
				ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
				response.close();
				List<Stop> stopList = a.getResult()
						.asList()
						.stream()
						.map(e-> e.as(Stop.class))
						.collect(Collectors.toList());

				restoreJsonIteratorMode();
				return stopList;
			} catch(IOException ex){
				return null;
			}
		} else {
			throw new GTFSrsError("Unable to get any response for this request");
		}
    }

	private static void restoreJsonIteratorMode() {
		JsonIterator.setMode(DecodingMode.DYNAMIC_MODE_AND_MATCH_FIELD_WITH_HASH);
		JsonStream.setMode(EncodingMode.DYNAMIC_MODE);
	}

	public List<Route> getRoutes() throws GTFSrsError {
        // /api/routes
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME)
                .addPathSegments("api/routes")
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
				response.close();
                return a.getResult().asList().stream().map(e-> e.as(Route.class)).collect(Collectors.toList());
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new GTFSrsError("Unable to get any response for this request");
        }
    }
    
    public Agency getAgency(String uid) throws GTFSrsError {
        // /api/stops/by-trip/<tid>
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME)
                .addPathSegments("api/agency/" + uid)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
				JsonIterator.setMode(DecodingMode.REFLECTION_MODE);
				JsonStream.setMode(EncodingMode.STATIC_MODE);
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
				response.close();
                Agency ag = a.getResult().as(Agency.class);
				restoreJsonIteratorMode();
				return ag;
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new GTFSrsError("Unable to get any response for this request");
        }
    }

    public Route getRoute(String uid) throws GTFSrsError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME)
                .addPathSegments("api/routes/" + uid)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                Result a = JsonIterator.deserialize(response.body().string()).as(Result.class);
                response.close();
                return a.result.as(Route.class);
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new GTFSrsError("Unable to get any response for this request");
        }
    }

    public List<Route> getRouteByStop(String uid) throws GTFSrsError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME)
                .addPathSegments("api/routes/by-stop/" + uid)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
				response.close();
                List<Route> routes = new ArrayList<>();
                a.getResult().asList().forEach(e->routes.add(e.as(Route.class)));
                return routes;
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new GTFSrsError("Unable to get any response for this request");
        }
    }

    public Trip getTrip(String uid) throws GTFSrsError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME)
                .addPathSegments("api/trips/" + uid)
                .build();
        Response response = client.get(url);
        if(response != null && response.isSuccessful() && response.body() != null){
            try {
            	JsonIterator.setMode(DecodingMode.REFLECTION_MODE);
            	JsonStream.setMode(EncodingMode.REFLECTION_MODE);
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
				response.close();
                Trip t = a.getResult().as(Trip.class);
                restoreJsonIteratorMode();
                return t;
            } catch(IOException ex){
                return null;
            }
        } else {
            throw new GTFSrsError("Unable to get any response for this request");
        }
    }

    public PaginatedList<Trip> getTripsByBBox(BoundingBox boundingBox) throws GTFSrsError {
        HttpUrl url = new HttpUrl.Builder()
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME)
                .addPathSegments("api/trips/in/" + boundingBox)
                .build();
        Response response = client.get(url, 120 * 1000);
		return getPaginatedTrips(response);
	}

	public PaginatedList<Trip> getTripsByBBox(BoundingBox boundingBox, TripSearch tripSearch) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/trips/in/" + boundingBox);

		addTripSearch(builder, tripSearch);

		HttpUrl url = builder.build();
		Response response = client.get(url, 120 * 1000);
		return getPaginatedTrips(response);
	}

	public PaginatedList<Trip> getTrips(TripSearch tripSearch) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/trips/");

		addTripSearch(builder, tripSearch);

		HttpUrl url = builder.build();
		Response response = client.get(url, 50*1000);
		return getPaginatedTrips(response);
	}

	public PaginatedList<StopTimes> getStopTimes(Time after, Coordinate near, double radius) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stop_times/after/")
				.addPathSegment(after.toString())
				.addPathSegment("near")
				.addPathSegment(String.valueOf(near.getLat()))
				.addPathSegment(String.valueOf(near.getLng()))
				.addPathSegment(String.valueOf(radius));

		HttpUrl url = builder.build();
		Response response = client.get(url);
		return getPaginatedStopTimes(response);
	}

	public PaginatedList<StopTimes> getStopTimesBetween(Time after, Time before, Coordinate near, double radius) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stop_times/between/")
				.addPathSegment(after.toString())
				.addPathSegment(before.toString())
				.addPathSegment("near")
				.addPathSegment(String.valueOf(near.getLat()))
				.addPathSegment(String.valueOf(near.getLng()))
				.addPathSegment(String.valueOf(radius));

		HttpUrl url = builder.build();
		Response response = client.get(url, 20 * 1000);
		return getPaginatedStopTimes(response);
	}

	public StopTimes getStopTimesBetween(Time after, Time before, Date date, Stop stop) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stop_times/by-stop/")
				.addPathSegment(stop.getUid())
				.addPathSegment("between")
				.addPathSegment(after.toString())
				.addPathSegment(before.toString())
				.addPathSegment(date.toString());

		HttpUrl url = builder.build();
		Response response = client.get(url, 50 * 1000);
		return parseSingleStopTimes(response);
	}

	public StopTimes getStopTimesByStop(String stop_uid, Time after) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stop_times/by-stop/")
				.addPathSegment(stop_uid)
				.addPathSegment("after")
				.addPathSegment(after.toString());

		HttpUrl url = builder.build();
		Response response = client.get(url);
		return parseSingleStopTimes(response);
	}

	private StopTimes parseSingleStopTimes(Response response) throws GTFSrsError {
		if(response != null && response.isSuccessful() && response.body() != null){
			try {
				Result a = JsonIterator.deserialize(
						response.body().string(),
						Result.class);
				JsonIterator.setMode(DecodingMode.REFLECTION_MODE);
				JsonStream.setMode(EncodingMode.DYNAMIC_MODE);
				StopTimes st = a.getResult()
								.as(StopTimes.class);
				restoreJsonIteratorMode();
				return st;
			} catch(IOException ex){
				return null;
			}
		} else {
			throw new GTFSrsError("Unable to get any response for this request");
		}
	}

	private PaginatedList<StopTimes> getPaginatedStopTimes(Response response) throws GTFSrsError {
		if(response != null && response.isSuccessful() && response.body() != null){
			try {
				ResultArray a = JsonIterator.deserialize(
						response.body().string(),
						ResultArray.class);
				response.close();
				JsonIterator.setMode(DecodingMode.REFLECTION_MODE);
				JsonStream.setMode(EncodingMode.STATIC_MODE);
				PaginatedList<StopTimes> pl = new PaginatedList<>(
						a.getResult()
								.asList()
								.stream()
								.map((e) -> e.as(StopTimes.class))
								.collect(Collectors.toList()),
						a.getMeta()
				);
				restoreJsonIteratorMode();
				return pl;
			} catch(IOException ex){
				return null;
			}
		} else {
			throw new GTFSrsError("Unable to get any response for this request");
		}
	}

	private void addTripSearch(HttpUrl.Builder builder, TripSearch tripSearch) {
		if(tripSearch.stops_visited != null){
			builder.addQueryParameter(
					"stops_visited",
					String.join(",", tripSearch.stops_visited)
			);
		}

		if(tripSearch.route != null){
			builder.addQueryParameter(
					"route",
					tripSearch.route
			);
		}

		if(tripSearch.departure_after != null){
			builder.addQueryParameter(
					"departure_after",
					tripSearch.departure_after.toString()
			);
		}

		if(tripSearch.arrival_before != null){
			builder.addQueryParameter(
					"arrival_before",
					tripSearch.arrival_before.toString()
			);
		}

		if(tripSearch.offset != null){
			builder.addQueryParameter(
					"offset",
					String.valueOf(tripSearch.offset)
			);
		}

		if(tripSearch.per_page != null){
			builder.addQueryParameter(
					"per_page",
					String.valueOf(tripSearch.per_page)
			);
		}

		if(tripSearch.sort_by != null){
			builder.addQueryParameter(
					"sort_by",
					tripSearch.sort_by.toString()
			);
		}

		if(tripSearch.sort_order != null){
			builder.addQueryParameter(
					"sort_order",
					tripSearch.sort_order.toString()
			);
		}
	}

	private List<Trip> getTrips(Response response) throws GTFSrsError {
		if(response != null && response.isSuccessful() && response.body() != null){
			try {
				ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
				return a.getResult()
						.asList()
						.stream()
						.map((e)-> e.as(Trip.class))
						.collect(Collectors.toList());
			} catch(IOException ex){
				return null;
			}
		} else {
			throw new GTFSrsError("Unable to get any response for this request");
		}
	}

	public static PaginatedList<Trip> parsePaginatedTrips(byte[] json){
		ResultArray a = JsonIterator.deserialize(
				json,
				ResultArray.class);
		return new PaginatedList<>(
				a.getResult()
						.asList()
						.stream()
						.map((e) -> e.as(Trip.class))
						.collect(Collectors.toList()),
				a.getMeta()
		);
	}

	public static PaginatedList<Trip> parsePaginatedTrips(String json){

    	Map<String, Object> resultArray = JsonParse.map(json);
    	List<Map<String, Object>> trips = (List<Map<String, Object>>) resultArray.get("result");
    	Map<String, Object> meta = (Map<String, Object>) resultArray.get("meta");
		Meta metaobj = new Meta();

		Map<String, Object> pag = (Map<String, Object>) meta.get("pagination");
		Map<String, Object> err = (Map<String, Object>) meta.get("error");

		if(pag != null){
			Pagination p = new Pagination();
			p.limit = ((Long) pag.get("limit")).intValue();
			p.offset = ((Long) pag.get("offset")).intValue();
			metaobj.pagination = p;
		}

		if(err != null){
			Error error = new Error();
			error.message = (String) err.get("message");
			error.code = ((Long) err.get("code")).intValue();
			metaobj.error = error;
		}

		metaobj.success = (boolean) meta.get("success");

    	List<Trip> trips_objs = trips.stream().map(e->{
    		Trip t = new Trip();
    		t.uid = (String) e.get("uid");
    		t.route_id = (String) e.get("route_id");
    		t.service_id = (String) e.get("service_id");
    		t.headsign = (String) e.get("headsign");
    		t.short_name = (String) e.get("short_name");
    		t.direction_id = ((int) (long) e.get("direction_id"));
    		t.stop_sequence = ((List<Map<String, Object>>) e.get("stop_sequence")).stream().map(ss->{
    			StopTrip st = new StopTrip();
    			Map<String, Object> stopMap = (Map<String, Object>) ss.get("stop");
    			Stop s = new Stop();
    			s.setUid((String) stopMap.get("uid"));
    			s.setName((String) stopMap.get("name"));
    			s.setLat((Double) stopMap.get("lat"));
    			s.setLng((Double) stopMap.get("lng"));
    			s.setLocation_type(((Long) stopMap.get("location_type")).intValue());
    			s.setParent_station((String) stopMap.get("parent_station"));

    			st.setStop(s);
    			st.setArrival_time((String) ss.get("arrival_time"));
    			st.setDeparture_time((String) ss.get("departure_time"));
    			st.setStop_sequence(((Long) ss.get("stop_sequence")).intValue());
    			st.setDrop_off(ch.supsi.dti.i2b.shrug.optitravel.models.DropOff.valueOf((String) ss.get("drop_off")));
    			st.setPickup(ch.supsi.dti.i2b.shrug.optitravel.models.PickUp.valueOf((String) ss.get("pickup")));
    			return st;
			}).collect(Collectors.toList());
    		return t;
		}).collect(Collectors.toList());

		PaginatedList<Trip> pl = new PaginatedList<>();
		pl.setResult(trips_objs);
		pl.setMeta(metaobj);
		return pl;
	}

	private PaginatedList<Trip> getPaginatedTrips(Response response) throws GTFSrsError {
		if(response != null && response.isSuccessful() && response.body() != null){
			try {
				PaginatedList<Trip> paginatedTrips = parsePaginatedTrips(response.body().string());
				response.close();
				return paginatedTrips;
			} catch(IOException ex){
				return null;
			}
		} else {
			throw new GTFSrsError("Unable to get any response for this request");
		}
	}

	public List<Stop> getStopsByBBox(BoundingBox boundingBox) throws GTFSrsError {
		HttpUrl url = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stops/in/" + boundingBox)
				.build();
		Response response = client.get(url, 20*1000);
		return parseStopResponse(response);
    }

	public Stop getStop(String uid) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stops/")
				.addPathSegment(uid);

		HttpUrl url = builder.build();
		Response response = client.get(url);
		return parseSingleStopResponse(response);
	}

	private Stop parseSingleStopResponse(Response response) throws GTFSrsError {
		if(response != null && response.isSuccessful() && response.body() != null){
			try {
				Result a = JsonIterator.deserialize(
						response.body().string(),
						Result.class);
				if(!a.meta.success){
					throw new GTFSrsError("Request not successful");
				}
				return a.result.as(Stop.class);
			} catch(IOException ex){
				return null;
			}
		} else {
			throw new GTFSrsError("Unable to get any response for this request");
		}
	}

	public PaginatedList<StopDistance> getStopsNear(Coordinate coordinate) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stops/near/")
				.addPathSegment(String.valueOf(coordinate.getLat()))
				.addPathSegment(String.valueOf(coordinate.getLng()));

		HttpUrl url = builder.build();
		Response response = client.get(url, 20 * 1000);
		return getPaginatedStopDistances(response);
	}

	public PaginatedList<StopDistance> getStopsNear(Coordinate coordinate, double radius) throws GTFSrsError {
		HttpUrl.Builder builder = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stops/near/")
				.addPathSegment(String.valueOf(coordinate.getLat()))
				.addPathSegment(String.valueOf(coordinate.getLng()))
				.addPathSegment(String.valueOf(radius));

		HttpUrl url = builder.build();
		Response response = client.get(url);
		return getPaginatedStopDistances(response);
	}

	private PaginatedList<StopDistance> getPaginatedStopDistances(Response response) throws GTFSrsError {
		if(response != null && response.isSuccessful() && response.body() != null){
			try {
				ResultArray a = JsonIterator.deserialize(
						response.body().string(),
						ResultArray.class);
				response.close();
				JsonIterator.setMode(DecodingMode.REFLECTION_MODE);
				JsonStream.setMode(EncodingMode.STATIC_MODE);
				PaginatedList<StopDistance> pl = new PaginatedList<>(
						a.getResult()
								.asList()
								.stream()
								.map((e) -> e.as(StopDistance.class))
								.collect(Collectors.toList()),
						a.getMeta()
				);
				restoreJsonIteratorMode();
				return pl;
			} catch(IOException ex){
				return null;
			}
		} else {
			throw new GTFSrsError("Unable to get any response for this request");
		}
	}

	public PaginatedList<Route> getRoutesByBBox(BoundingBox boundingBox) {
    	// Not Yet Implemented
		return null;
	}

	public PaginatedList<StopTimes> getStopTimesInBBoxBetween(BoundingBox boundingBox,
															  Time start_time,
															  Time end_time) throws GTFSrsError {
		HttpUrl url = new HttpUrl.Builder()
				.host(HOST)
				.port(PORT)
				.scheme(SCHEME)
				.addPathSegments("api/stop_times/between/")
				.addPathSegment(start_time.toString())
				.addPathSegment(end_time.toString())
				.addPathSegment("in")
				.addPathSegment(boundingBox.toString())
				.build();
		Response response = client.get(url, 20 * 1000);
		return getPaginatedStopTimes(response);
	}
}
