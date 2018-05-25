package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.api.StopTrip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Trip extends ch.supsi.dti.i2b.shrug.optitravel.models.Trip {
    public String uid;
    public String route_id;
    public String service_id;
    public String headsign;
    public String short_name;
    public int direction_id;
    public List<StopTrip> stop_sequence;

    public Trip(){

	}

	public Trip(String trip) {
		this.uid = trip;
	}

	public String getUID() {
        return this.uid;
    }
    public List<ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip> getStopTrip() {
    	return new ArrayList<>(stop_sequence);
    }
    public Route getRoute() {
        return new Route(route_id);
    }

    public String getHeadSign() {
        return headsign;
    }

	public List<StopTrip> getStopSequence() {
		return stop_sequence;
	}

	public int getDirectionId() {
		return direction_id;
	}

	public String getServiceId() {
		return service_id;
	}

	public String getShortName() {
		return short_name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid, route_id, service_id, headsign, short_name,
				direction_id, stop_sequence);
	}
}
