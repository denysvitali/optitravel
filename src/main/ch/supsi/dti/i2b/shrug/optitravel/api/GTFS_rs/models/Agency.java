package ch.supsi.dti.i2b.shrug.optitravel.api.GTFS_rs.models;

import ch.supsi.dti.i2b.shrug.optitravel.models.Operator;

public class Agency extends Operator {
    String uid;
    String name;
    String url;
    String timezone;
    String lang;
    String phone;

    public Agency(){

    }

    public Agency(String agencyUID) {
        // TODO: Fetch other fields from API? (@denvit)
        this.uid = agencyUID;
    }

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

    @Override
    public String toString() {
        return String.format("%s", getUid());
    }
}
