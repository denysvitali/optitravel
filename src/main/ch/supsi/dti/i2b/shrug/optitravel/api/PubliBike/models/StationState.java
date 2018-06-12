package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import com.jsoniter.annotation.JsonObject;

@JsonObject
public class StationState {
    private int id;
    private String name;

    public StationState(){}

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
