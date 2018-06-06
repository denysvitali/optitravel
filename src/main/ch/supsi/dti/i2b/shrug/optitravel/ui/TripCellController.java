package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.models.Trip;
import ch.supsi.dti.i2b.shrug.optitravel.models.WaitingTrip;
import ch.supsi.dti.i2b.shrug.optitravel.models.WalkingTrip;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.IOException;

public class TripCellController {

    @FXML
    private Label from;
    @FXML
    private Label eta;
    @FXML
    private Label to;
    @FXML
    private Label distance;
    @FXML
    private Label details;
    @FXML
    private MaterialIconView icon;

    private Node view;

    public TripCellController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/listCellItem.fxml"));
        loader.setController(this);
        try {
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTrip(Trip trip) {
        distance.setVisible(false);
        details.setVisible(false);
        if(trip instanceof WalkingTrip) {
            from.textProperty().setValue("Walk to next stop.");
            to.setVisible(false);
            icon.setIcon(MaterialIcon.DIRECTIONS_WALK);
        } else {
            icon.setIcon(MaterialIcon.DIRECTIONS_BUS);
            from.textProperty().setValue(trip.getStopTrip().get(0).getStop().getName());
            to.textProperty().setValue(trip.getRoute().getName());
        }
        eta.textProperty().setValue(trip.getStopTrip().get(0).getDeparture().toString());
    }

    public Node getView() {
        return view;
    }
}
