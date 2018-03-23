package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

public class Agency {
    String uid;
    String name;
    String url;
    String timezone;
    String lang;
    String phone;

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getURL() {
        return url;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getLang() {
        return lang;
    }

    public String getPhone() {
        return phone;
    }
}
