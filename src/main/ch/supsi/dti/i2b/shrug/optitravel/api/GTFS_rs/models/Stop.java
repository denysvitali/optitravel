package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

import java.util.Objects;

public class Stop extends ch.supsi.dti.i2b.shrug.optitravel.models.Stop {
    private String uid;
    private String name;
    private double lat;
    private double lng;
    private int location_type;
    private String parent_station;

    public String getName() {
        return this.name;
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
		return Double.compare(stop.lat, lat) == 0 &&
				Double.compare(stop.lng, lng) == 0 &&
				location_type == stop.location_type &&
				Objects.equals(uid, stop.uid) &&
				Objects.equals(name, stop.name) &&
				Objects.equals(parent_station, stop.parent_station);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uid, name, lat, lng, location_type, parent_station);
	}
}
