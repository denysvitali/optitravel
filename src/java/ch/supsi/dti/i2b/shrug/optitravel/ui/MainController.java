package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.GeocodingService;
import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Place;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.TripTimeFrame;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lynden.gmapsfx.GoogleMapView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class MainController {

    @FXML
    private JFXTimePicker tpTime;
    @FXML
    private JFXDatePicker dpDate;
    @FXML
    private Button fabSend;
    @FXML
    private ComboBox<TripTimeFrame> cbTripPeriod;
    @FXML
    private AutoCompleteTextField tfStartPoint;
    @FXML
    private AutoCompleteTextField tfEndPoint;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private JFXListView<Stop> lvRouteStops;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private AnchorPane filtersContainer;

    private MapController mapController;
    private Place origin;
    private Place destination;

    public MainController() {
    }

    @FXML
    private void initialize() {

        // Save geocoded place from textfields on selection and add marker to map
        tfStartPoint.setLocationGeocodedListener((origin, value) ->
                mapController.addMarker(value.getGeometry().getLocation(), MapController.NodeType.ORIGIN));
        tfEndPoint.setLocationGeocodedListener((origin, value) ->
                mapController.addMarker(value.getGeometry().getLocation(), MapController.NodeType.DESTINATION));

        // Validation for textfields
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("This field is required.");
        tfStartPoint.getValidators().add(requiredFieldValidator);
        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("This field is required.");
        tfEndPoint.getValidators().add(requiredFieldValidator);
        tfStartPoint.focusedProperty().addListener((o,oldVal,newVal) -> {if(!newVal) tfStartPoint.validate();});
        tfEndPoint.focusedProperty().addListener((o,oldVal,newVal) -> {if(!newVal) tfEndPoint.validate();});



        // Initialise map
        mapController = new MapController(mapView, this);
        mapView.addMapInializedListener(mapController);

        // Setup trip period selection combobox
        cbTripPeriod.getSelectionModel().selectFirst();
        cbTripPeriod.getItems().addAll(TripTimeFrame.LEAVE_NOW, TripTimeFrame.DEPART_AT, TripTimeFrame.ARRIVE_BY);
        // Set date and time pickers to hide when combobox is set to "leave now"
        cbTripPeriod.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case LEAVE_NOW:
                    tpTime.setManaged(false);
                    dpDate.setManaged(false);
                    tpTime.setVisible(false);
                    dpDate.setVisible(false);
                    break;
                default:
                    tpTime.setManaged(true);
                    dpDate.setManaged(true);
                    tpTime.setVisible(true);
                    dpDate.setVisible(true);
                    break;
            }
        });
        cbTripPeriod.getSelectionModel().selectFirst();

        // Set pickers to open when clicking on text field
        dpDate.getEditor().setOnMouseClicked(e -> dpDate.show());
        tpTime.getEditor().setOnMouseClicked(e -> tpTime.show());
        tpTime.setIs24HourView(true);

        // Prepare listview
        lvRouteStops.setPrefWidth(280);
        mainContainer.heightProperty().addListener((observable, oldValue, newValue) -> lvRouteStops.setPrefHeight(mainContainer.getHeight() - filtersContainer.getHeight() + 8 - fabSend.getPrefHeight() / 2));
        filtersContainer.heightProperty().addListener((observable, oldValue, newValue) -> lvRouteStops.setPrefHeight(mainContainer.getHeight() - filtersContainer.getHeight() + 8 - fabSend.getPrefHeight() / 2));
        lvRouteStops.setCellFactory(new Callback<ListView<Stop>, ListCell<Stop>>() {
            @Override
            public ListCell<Stop> call(ListView<Stop> param) {
                return new StopCellItem();
            }
        });
        for (int i = 0; i < 10; i++)
            lvRouteStops.getItems().addAll(new Stop() {
                @Override
                public String getName() {
                    return "name";
                }

                @Override
                public Coordinate getCoordinate() {
                    return new Coordinate(0, 0);
                }
            });

        fabSend.toFront();
        lvRouteStops.toBack();

    }
}

