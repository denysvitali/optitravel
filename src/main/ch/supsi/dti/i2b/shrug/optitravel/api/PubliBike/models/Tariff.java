package ch.supsi.dti.i2b.shrug.optitravel.api.PubliBike.models;

import java.util.List;

public class Tariff {
    private int id;
    private String name;
    private String description;
    private String base_price;
    private List<TariffModel> tariff_models;
    private boolean free;
    private Integer upgrade_tariff_id;
    private boolean auto_renewal;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBasePrice() {
        return base_price;
    }

    public List<TariffModel> getTariffModels() {
        return tariff_models;
    }

    public boolean isFree() {
        return free;
    }

    public int getUpgradeTariffId() {
        return upgrade_tariff_id;
    }

    public boolean isAutoRenewal() {
        return auto_renewal;
    }
}
