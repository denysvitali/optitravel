package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;

@JsonObject
public class Network {
	@JsonProperty("id")
    private int id;
	@JsonProperty("name")
    private String name;
	@JsonProperty("background_img")
    private String background_img;
	@JsonProperty("logo_img")
    private String logo_img;
	@JsonProperty("sponsors")
    private ArrayList<String> sponsors;
    public Network(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBackground_img() {
        return background_img;
    }

    public String getLogo_img() {
        return logo_img;
    }

    public ArrayList<String> getSponsors() {
        return sponsors;
    }
}
