package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

public class Stop {
    private String uid;
    private String name;
    private float lat;
    private float lng;
    private String type;
    private String parent_station;

    public String getName() {
        return this.name;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getUid() {
        return uid;
    }
}
