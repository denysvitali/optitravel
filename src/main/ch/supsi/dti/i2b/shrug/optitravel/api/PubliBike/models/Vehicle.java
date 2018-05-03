package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

public class Vehicle {
    private int id;
    private String name;
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
