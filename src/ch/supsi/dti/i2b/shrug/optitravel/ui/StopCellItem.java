package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import javafx.scene.control.ListCell;

public class StopCellItem extends ListCell<Stop> {

    @Override
    protected void updateItem(Stop item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null) {
            StopCellController cellController = new StopCellController();
            cellController.setStop(item);
        }
    }
}
