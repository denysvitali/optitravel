package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.response;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Place;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.any.Any;

import java.util.List;

public class GeocodingResponse {

    private final List<Place> places;
    private final List<Any> htmlAttributions;
    private final String status;

    @JsonCreator
    public GeocodingResponse(@JsonProperty("results") List<Place> places,
                             @JsonProperty("html_attributions") List<Any> htmlAttributions,
                             @JsonProperty("status") String status) {
        this.places = places;
        this.htmlAttributions = htmlAttributions;
        this.status = status;
    }

    public List<Any> getHtmlAttributions() {
        return htmlAttributions;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public String getStatus() {
        return status;
    }
}
