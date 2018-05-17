package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.IOException;

public class StopCellController {

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

    public StopCellController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/listCellItem.fxml"));
        loader.setController(this);
        try {
            view = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStop(Stop stop) {

    }

    public Node getView() {
        return view;
    }
}
