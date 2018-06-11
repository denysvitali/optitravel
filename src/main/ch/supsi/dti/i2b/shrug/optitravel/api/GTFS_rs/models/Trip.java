package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import com.jsoniter.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trip extends ch.supsi.dti.i2b.shrug.optitravel.models.Trip {
	private static final long serialVersionUID = 238604785615237409L;
    public String uid;
    public String route_id;
    public String service_id;
    public String headsign;
    public String short_name;
    public int direction_id;
    public List<StopTrip> stop_sequence;

    @JsonIgnore
    private Route route;

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
        if(route != null){
        	return route;
		}
    	return new Route(route_id);
    }

	@Override
	public void setStopTrip(List<ch.supsi.dti.i2b.shrug.optitravel.models.StopTrip> stopTrip) {
    	if(stopTrip != null) {
			this.stop_sequence = new ArrayList<>();
			stopTrip.forEach(e -> stop_sequence.add((StopTrip) e));
		}
	}

	@Override
	public String getHeadSign() {
        return headsign;
    }

	@Override
	public void setRoute(ch.supsi.dti.i2b.shrug.optitravel.models.Route route) {
		this.route = (Route) route;
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

	@Override
	public String toString() {
    	if(getHeadSign() != null){
			return String.format("%s (%s)", getHeadSign(), getUID());
		}
		return String.format("%s", getUID());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Trip trip = (Trip) o;
		return Objects.equals(uid, trip.uid);
	}
}
