package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject
public class Sponsor {
	@JsonProperty("id")
    private int id;
	@JsonProperty("name")
    private String name;
	@JsonProperty("image")
    private String image;
	@JsonProperty("url")
    private String url;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
