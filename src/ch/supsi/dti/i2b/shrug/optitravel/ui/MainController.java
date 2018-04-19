package ch.supsi.dti.i2b.shrug.optitravel.ui;

import com.jfoenix.controls.*;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.ResourceBundle;

public class MainController {

    @FXML
    public JFXTimePicker tpTime;
    @FXML
    public JFXDatePicker dpDate;
    @FXML
    public JFXButton fabSend;
    public FXCollections tripPeriodOptions;
    @FXML
    private JFXTextField tfEndPoint;
    @FXML
    private ComboBox cbTripPeriod;
    @FXML
    private JFXTextField tfStartPoint;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private ResourceBundle resourceBundle;


    private MapController mapController;

    @FXML
    private void initialize() {

        mapController = new MapController(mapView);
        mapView.addMapInializedListener(mapController);

        cbTripPeriod.getSelectionModel().selectFirst();

        dpDate.getEditor().setOnMouseClicked(e -> dpDate.show());
        tpTime.getEditor().setOnMouseClicked(e -> tpTime.show());
        tpTime.setIs24HourView(true);

    }


}
