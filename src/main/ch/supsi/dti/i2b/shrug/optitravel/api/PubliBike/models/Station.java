package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeError;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonIgnore;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.any.Any;

import java.util.ArrayList;

@JsonObject
public class Station extends Location {
    @JsonProperty("id")
    private int id;
    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("logitude")
    private double longitude;
	@JsonProperty("state")
    private StationState state;

	@JsonProperty("name")
    private String name;
	@JsonProperty("address")
    private String address;
	@JsonProperty("zip")
    private String zip;
	@JsonProperty("city")
	private String city;

	@JsonProperty("vehicles")
    private ArrayList<Vehicle> vehicles;
	@JsonProperty("network")
    private Network network;
	@JsonProperty("sponsors")
    private ArrayList<Sponsor> sponsors;

    @JsonIgnore private PubliBikeWrapper pbw;

    @Override
    public Coordinate getCoordinate() {
        return new Coordinate(latitude, longitude);
    }

    public int getId() {
        return id;
    }

    public StationState getState() {
        return state;
    }

    @Override
    public String toString() {
        return String.format("%s [ID: %d @ (%.4f,%.4f)]",
                getName(), getId(), getCoordinate().getLat(), getCoordinate().getLng());
    }

    private void update(Station s){
        if(s!=null){
            this.name = s.name;
            this.address = s.address;
            this.vehicles = s.vehicles;
            this.city = s.city;
            this.zip = s.zip;
            this.network = s.network;
            this.sponsors = s.sponsors;
            this.state = s.state;
        }
    }

    public String getName() {
        if(name != null){
            return name;
        }

        if(pbw != null){
            try {
                Station newStation = pbw.getStation(this.id);
                update(newStation);
                return this.name;
            } catch (PubliBikeError publiBikeError) {
                return null;
            }
        } else {
            return null;
        }
    }

    public ArrayList<Vehicle> getVehicles() {
        if(vehicles != null){
            return vehicles;
        }

        if(pbw != null){
            try {
                Station newStation = pbw.getStation(this.id);
                update(newStation);
                return this.vehicles;
            } catch (PubliBikeError publiBikeError) {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getZip() {
        if(zip != null){
            return zip;
        }

        if(pbw != null){
            try {
                Station newStation = pbw.getStation(this.id);
                update(newStation);
                return this.zip;
            } catch (PubliBikeError publiBikeError) {
                return null;
            }
        } else {
            return null;
        }
    }

    public Network getNetwork() {
        if(network != null){
            return network;
        }

        if(pbw != null){
            try {
                Station newStation = pbw.getStation(this.id);
                update(newStation);
                return this.network;
            } catch (PubliBikeError publiBikeError) {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getAddress() {
        if(address != null){
            return address;
        }

        if(pbw != null){
            try {
                Station newStation = pbw.getStation(this.id);
                update(newStation);
                return this.address;
            } catch (PubliBikeError publiBikeError) {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getCity() {
        if(city != null){
            return city;
        }

        if(pbw != null){
            try {
                Station newStation = pbw.getStation(this.id);
                update(newStation);
                return this.city;
            } catch (PubliBikeError publiBikeError) {
                return null;
            }
        } else {
            return null;
        }
    }

    public ArrayList<Sponsor> getSponsors() {
        if(sponsors != null){
            return sponsors;
        }

        if(pbw != null){
            try {
                Station newStation = pbw.getStation(this.id);
                update(newStation);
                return this.sponsors;
            } catch (PubliBikeError publiBikeError) {
                return null;
            }
        } else {
            return null;
        }
    }

    @JsonIgnore
    public Station setWrapper(PubliBikeWrapper publiBikeWrapper) {
        this.pbw = publiBikeWrapper;
        return this;
    }
}
