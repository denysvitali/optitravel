package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

public class TariffModel {
    private VehicleType vehicle_type;
    private String fixed_price;
    private String fixed_price_description;
    private String var_price;
    private String var_price_description;
    private String max_price;
    private String max_price_description;

    public VehicleType getVehicleType() {
        return vehicle_type;
    }

    public String getFixedPrice() {
        return fixed_price;
    }

    public String getFixedPriceDescription() {
        return fixed_price_description;
    }

    public String getVarPrice() {
        return var_price;
    }

    public String getVarPriceDescription() {
        return var_price_description;
    }

    public String getMaxPrice() {
        return max_price;
    }

    public String getMaxPriceDescription() {
        return max_price_description;
    }
}
