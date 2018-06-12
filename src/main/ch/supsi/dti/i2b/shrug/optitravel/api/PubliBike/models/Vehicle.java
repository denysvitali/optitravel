package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject
public class Vehicle {
	@JsonProperty("id")
    private int id;
	@JsonProperty("name")
    private String name;
	@JsonProperty("type")
    private VehicleType type;

    public Vehicle(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VehicleType getType() {
        return type;
    }
}
