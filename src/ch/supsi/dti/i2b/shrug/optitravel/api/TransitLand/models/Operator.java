package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models;

import java.util.ArrayList;

public class Operator extends ch.supsi.dti.i2b.shrug.optitravel.models.Operator {
    private String onestop_id;
    private String name;
    private String short_name;
    private String website;
    private String country;
    private String timezone;
    private String state;
    private String metro;
    private Geometry geometry;
    private String created_at;
    private String updated_at;
    private ArrayList<String> represented_in_feed_onestop_ids;

    public Operator(){

    }

    public String getId() {
        return onestop_id;
    }

    public String getShortName() {
        return short_name;
    }

    public String getURL() {
        return website;
    }

    public String getCountry() {
        return country;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getState() {
        return state;
    }

    public String getMetro() {
        return metro;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public ArrayList<String> getRepresentedInFeedOnestopIds() {
        return represented_in_feed_onestop_ids;
    }

    public String getName() {
        return name;
    }

}
