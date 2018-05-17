package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.*;
import java.util.Date;

public class TestStop extends Stop {

    private String from;
    private OffsetDateTime eta;
    private String to;
    private double distance;
    private MaterialIcon icon;

    public TestStop() {
        this.from = "aaaaaa";
        this.eta = OffsetDateTime.now();
        this.to = "aaaaaa";
        this.distance = 69;
        this.icon = MaterialIcon.ADJUST;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }

    public String getFrom() {
        return from;
    }

    public OffsetDateTime getEta() {
        return eta;
    }

    public String getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    public MaterialIcon getIcon() {
        return icon;
    }
}
