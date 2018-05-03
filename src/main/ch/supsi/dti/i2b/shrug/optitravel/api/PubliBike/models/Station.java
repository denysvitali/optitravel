package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeError;
import ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.PubliBikeWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;

import java.util.ArrayList;

public class Station extends Location {
    private int id;
    private double latitude;
    private double longitude;
    private StationState state;

    private String name;
    private String address;
    private int zip = -1;
    private String city;

    private ArrayList<Vehicle> vehicles;
    private Network network;
    private ArrayList<String> sponsors;

    private PubliBikeWrapper pbw;

    Station(int id){
        this.id = id;
    }

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
        }
    }

    private String getName() {
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

    public int getZip() {
        if(zip != -1){
            return zip;
        }

        if(pbw != null){
            try {
                Station newStation = pbw.getStation(this.id);
                update(newStation);
                return this.zip;
            } catch (PubliBikeError publiBikeError) {
                return -1;
            }
        } else {
            return -1;
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

    public ArrayList<String> getSponsors() {
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

    public Station setWrapper(PubliBikeWrapper publiBikeWrapper) {
        this.pbw = publiBikeWrapper;
        return this;
    }
}
