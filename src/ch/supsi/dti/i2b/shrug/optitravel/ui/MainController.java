package ch.supsi.dti.i2b.shrug.optitravel.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private JFXTextField tfEndPoint;
    @FXML
    private JFXComboBox cbTripPeriod;
    @FXML
    private JFXTextField tfStartPoint;
    @FXML
    private GoogleMapView mapView;


    private MapController mapController;

    @FXML
    private void initialize() {
        mapController = new MapController(mapView);
        mapView.addMapInializedListener(mapController);

        cbTripPeriod.getSelectionModel().selectFirst();
    }


}
