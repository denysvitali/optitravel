package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.models.Trip;
import com.jfoenix.controls.JFXListCell;
import javafx.scene.Node;

public class TripCellItem extends JFXListCell<Trip> {

    private final TripCellController controller = new TripCellController();
    private final Node view = controller.getView();

    @Override
    protected void updateItem(Trip item, boolean empty) {
//        super.updateItem(item, empty);
        if(empty) setGraphic(null);
        else {
            controller.setTrip(item);
            setGraphic(view);
        }
    }
}
