package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

public class Stop {
    private String uid;
    private String name;
    private double lat;
    private double lng;
    private int location_type;
    private String parent_station;

    public String getName() {
        return this.name;
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
}
