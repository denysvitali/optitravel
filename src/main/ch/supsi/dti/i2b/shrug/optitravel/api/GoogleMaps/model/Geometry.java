package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

public class Geometry {
    private final Location location;


    @JsonCreator
    public Geometry(@JsonProperty("location") Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
