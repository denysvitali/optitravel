package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.GeocodingWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Place;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.Callback;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.lynden.gmapsfx.GoogleMapView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;

public class MainController {

    @FXML
    public JFXTimePicker tpTime;
    @FXML
    public JFXDatePicker dpDate;
    @FXML
    public JFXButton fabSend;
    @FXML
    private ComboBox cbTripPeriod;
    @FXML
    private AutoCompleteTextField tfStartPoint;
    @FXML
    private AutoCompleteTextField tfEndPoint;
    @FXML
    private GoogleMapView mapView;

    private MapController mapController;

    public MainController() {
    }

    @FXML
    private void initialize() {

        tfStartPoint.setOnSelect((e) -> {
            GeocodingWrapper.getPlacesAsync(tfStartPoint.getSelectedEntry().getDescription(), new Callback<List<Place>>() {
                @Override
                public void exec(List<Place> places) {
                    Platform.runLater(() -> mapController.addMarker(places.get(0).getGeometry().getLocation(), MapController.NodeType.ORIGIN));
                }
            });
        });
        tfEndPoint.setOnSelect((e) -> {
            GeocodingWrapper.getPlacesAsync(tfEndPoint.getSelectedEntry().getDescription(), new Callback<List<Place>>() {
                @Override
                public void exec(List<Place> places) {
                    Platform.runLater(() -> mapController.addMarker(places.get(0).getGeometry().getLocation(), MapController.NodeType.DESTINAION));
                }
            });
        });

        mapController = new MapController(mapView, this);
        mapView.addMapInializedListener(mapController);

        cbTripPeriod.getSelectionModel().selectFirst();

        dpDate.getEditor().setOnMouseClicked(e -> dpDate.show());
        tpTime.getEditor().setOnMouseClicked(e -> tpTime.show());
        tpTime.setIs24HourView(true);

    }
}
