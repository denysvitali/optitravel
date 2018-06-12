package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject
public class VehicleType {
	@JsonProperty("id")
    private int id;
	@JsonProperty("name")
    private String name;

    public VehicleType(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
