package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

public class Location {
    private final double lat;
    private final double lng;

    @JsonCreator
    public Location(@JsonProperty("lat") double lat, @JsonProperty("lng") double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLongitude() {
        return lng;
    }

    public double getLatitude() {
        return lat;
    }
}
