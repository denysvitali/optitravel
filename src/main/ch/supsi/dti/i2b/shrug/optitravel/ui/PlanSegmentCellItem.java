package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.models.plan.PlanSegment;
import com.jfoenix.controls.JFXListCell;
import javafx.scene.Node;

public class PlanSegmentCellItem extends JFXListCell<PlanSegment> {

    private final PlanSegmentCellController controller = new PlanSegmentCellController();
    private final Node view = controller.getView();

    @Override
    protected void updateItem(PlanSegment item, boolean empty) {
//        super.updateItem(item, empty);
        if(empty) setGraphic(null);
        else {
            controller.setTrip(item);
            setGraphic(view);
        }
    }
}
