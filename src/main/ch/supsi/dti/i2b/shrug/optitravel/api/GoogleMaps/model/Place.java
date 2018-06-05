package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

import java.util.List;

public class Place {

    private final String formattedAddress;
    private final Geometry geometry;
    private final String id;
    private final String name;
    private final List<String> types;

    @JsonCreator
    public Place(@JsonProperty("formatted_address") String formattedAddress,
                 @JsonProperty("geometry") Geometry geometry,
                 @JsonProperty("id") String id,
                 @JsonProperty("name") String name,
                 @JsonProperty("types") List<String> types) {
        this.formattedAddress = formattedAddress;
        this.geometry = geometry;
        this.id = id;
        this.name = name;
        this.types = types;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public Coordinate getCoordinates() {
        return geometry.getLocation().getCoordinate();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return formattedAddress;
    }
}