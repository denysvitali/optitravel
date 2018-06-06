package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.Result;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.ResultArray;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.config.BuildConfig;
import ch.supsi.dti.i2b.shrug.optitravel.geography.BoundingBox;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Date;
import ch.supsi.dti.i2b.shrug.optitravel.models.StopTime;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.HttpClient;
import com.jsoniter.JsonIterator;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GTFSrsWrapper {
    public static final String HOST = (BuildConfig.isDev() && !BuildConfig.USE_GTFS_REMOTE ? "localhost": "gtfs.ded1.denv.it");
    public static final int PORT = (BuildConfig.isDev() && !BuildConfig.USE_GTFS_REMOTE ? 8000 : 443);
    public static final String SCHEME = (BuildConfig.isDev() && !BuildConfig.USE_GTFS_REMOTE ? "http" : "https");
    private HttpClient client;

    public GTFSrsWrapper(){
        this.client = new HttpClient();
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
                return response.isSuccessful();
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
				ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
				return a.getResult()
						.asList()
						.stream()
						.map(e-> e.as(Stop.class))
						.collect(Collectors.toList());
			} catch(IOException ex){
				return null;
			}
		} else {
			throw new GTFSrsError("Unable to get any response for this request");
		}
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
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
                return a.getResult().as(Agency.class);
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
                ResultArray a = JsonIterator.deserialize(response.body().string(), ResultArray.class);
                return a.getResult().as(Trip.class);
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
        Response response = client.get(url, 20 * 1000);
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
		Response response = client.get(url);
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
				return a.getResult()
								.as(StopTimes.class);
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
				return new PaginatedList<>(
						a.getResult()
								.asList()
								.stream()
								.map((e) -> e.as(StopTimes.class))
								.collect(Collectors.toList()),
						a.getMeta()
				);
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
					tripSearch.departure_after
			);
		}

		if(tripSearch.arrival_before != null){
			builder.addQueryParameter(
					"arrival_before",
					tripSearch.arrival_before
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

	private PaginatedList<Trip> getPaginatedTrips(Response response) throws GTFSrsError {
		if(response != null && response.isSuccessful() && response.body() != null){
			try {
				return parsePaginatedTrips(response.body().string());
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
		Response response = client.get(url);
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
		Response response = client.get(url);
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
				return new PaginatedList<>(
						a.getResult()
								.asList()
								.stream()
								.map((e) -> e.as(StopDistance.class))
								.collect(Collectors.toList()),
						a.getMeta()
				);
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
		Response response = client.get(url);
		return getPaginatedStopTimes(response);
	}
}
