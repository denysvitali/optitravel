package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import java.util.ArrayList;

public class Network {
    private int id;

    private String name;

    private String background_img;
    private String logo_img;
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
