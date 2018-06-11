package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsError;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.GTFSrsWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.search.TripSearch;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.StopTime;
import ch.supsi.dti.i2b.shrug.optitravel.models.Time;
import com.jsoniter.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stop extends ch.supsi.dti.i2b.shrug.optitravel.models.Stop {
    private String uid;
    private String name;
    private double lat;
    private double lng;
    private int location_type;
    private String parent_station;

    public Stop(){

	}

    public Stop(String uid){
		this.uid = uid;
	}

    @JsonIgnore
    private GTFSrsWrapper gtfSrsWrapper;

    public String getName() {
        return this.name;
    }

	@JsonIgnore
    public void setWrapper(GTFSrsWrapper wrapper){
    	gtfSrsWrapper = wrapper;
	}

    @Override
    public Coordinate getCoordinate() {
        return new Coordinate(lat, lng);
    }

	public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getUid() {
        return uid;
    }

	public ch.supsi.dti.i2b.shrug.optitravel.models.Stop getParentStop() {
    	if(parent_station != null) {
			return new Stop(parent_station);
		}
		return null;
	}

	public int getType() {
        return location_type;
    }

    @Override
    public String toString() {
        return String.format("\"%s\" - %s (%s)",
				getName(),
				getUid(),
				getCoordinate());
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Stop stop = (Stop) o;
		return Objects.equals(this.uid, stop.uid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid, name, lat, lng, location_type, parent_station);
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setLocation_type(int location_type) {
		this.location_type = location_type;
	}

	public void setParent_station(String parent_station) {
		this.parent_station = parent_station;
	}

	public void setGtfSrsWrapper(GTFSrsWrapper gtfSrsWrapper) {
		this.gtfSrsWrapper = gtfSrsWrapper;
	}
}
