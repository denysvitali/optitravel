package ch.supsi.dti.i2b.shrug.optitravel.api.TransitFeed;

public class Location {
    private int id;
    private int pid;
    private String t;
    private String n;
    private double lat;
    private double lng;

    public Location(){

    }

    public String getName() {
        return n;
    }
}
