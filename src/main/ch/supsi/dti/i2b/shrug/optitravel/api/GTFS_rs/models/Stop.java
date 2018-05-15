package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;

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
    public void findNeighbours(int arrivalTime) {

    }
}
