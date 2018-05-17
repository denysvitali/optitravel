package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import com.jfoenix.controls.JFXListCell;
import javafx.scene.Node;

public class StopCellItem extends JFXListCell<Stop> {

    private final StopCellController controller = new StopCellController();
    private final Node view = controller.getView();

    @Override
    protected void updateItem(Stop item, boolean empty) {
//        super.updateItem(item, empty);
        if(empty) setGraphic(null);
        else {
            controller.setStop(item);
            setGraphic(view);
        }
    }
}
