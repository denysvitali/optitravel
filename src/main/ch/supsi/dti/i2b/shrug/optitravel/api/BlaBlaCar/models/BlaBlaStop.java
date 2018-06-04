package ch.supsi.dti.i2b.shrug.optitravel.api.BlaBlaCar.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonIgnore;
import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.any.Any;

public class BlaBlaStop extends Stop {

    private String city_name;
    private String address;
    private String country_code;

    @JsonIgnore
    private Coordinate coordinates;

    @JsonCreator
    public BlaBlaStop(@JsonProperty("latitude")double latitude, @JsonProperty("longitude")double longitude, String city_name, String address, String country_code){
        this.coordinates = new Coordinate(latitude, longitude);
        this.address = address;
        this.country_code = country_code;
        this.city_name = city_name;
    }


    @Override
    public String getName() {
        return city_name;
    }

    @Override
    public void findNeighbours(int arrivalTime) {

    }

    @Override
    public Coordinate getCoordinate() {

        return coordinates;
    }
}
