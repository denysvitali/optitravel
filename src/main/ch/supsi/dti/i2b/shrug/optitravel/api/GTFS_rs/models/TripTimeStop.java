package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.Time;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject
public class TripTimeStop {
	@JsonProperty("trip")
	public String trip;
	@JsonProperty("time")
	public String time;
	@JsonProperty("next_stop")
	public String next_stop;

	public TripTimeStop(){
	}

	public Time getTime() {
		return new Time(time);
	}

	public String getTrip() {
		return trip;
	}

	public String getNextStop() {
		return next_stop;
	}
}
