package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.params.DefaultPlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.params.DenvitPlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.planner.DataGathering;
import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.planner.Planner;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.MockPlanner;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.TripTimeFrame;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.service.directions.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

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
        tfStartPoint.getValidators().add(new PlaceValidator("Please select a valid place."));
        requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("This field is required.");
        tfEndPoint.getValidators().add(requiredFieldValidator);
        tfEndPoint.getValidators().add(new PlaceValidator("Please select a valid place."));


        tfStartPoint.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) tfStartPoint.validate();
        });
        tfEndPoint.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) tfEndPoint.validate();
        });

        // Initialise map
        mapController = new MapController(mapView, this);
        mapView.addMapInializedListener(mapController);


        // Set pickers to open when clicking on text field
        dpDate.getEditor().setOnMouseClicked(e -> dpDate.show());
        tpTime.getEditor().setOnMouseClicked(e -> tpTime.show());
        tpTime.setIs24HourView(true);
        // Validation for date and time pickers.
        dpDate.setDayCellFactory((picker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.compareTo(LocalDate.now()) < 0);
            }
        });

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

        // Prepare listview
        lvRouteStops.setPrefWidth(280);
        mainContainer.heightProperty().addListener((observable, oldValue, newValue) -> lvRouteStops.setPrefHeight(mainContainer.getHeight() - filtersContainer.getHeight() + 8 - fabSend.getPrefHeight() / 2));
        filtersContainer.heightProperty().addListener((observable, oldValue, newValue) -> lvRouteStops.setPrefHeight(mainContainer.getHeight() - filtersContainer.getHeight() + 8 - fabSend.getPrefHeight() / 2));
        lvRouteStops.setCellFactory(param -> new StopCellItem());

        fabSend.toFront();
        lvRouteStops.toBack();

        fabSend.setOnAction((event) -> validateAndRequest());
    }

    private void validateAndRequest() {
        if (!tfStartPoint.validate() || !tfEndPoint.validate()) {
            return;
        }
        mapController.fitToBounds(tfStartPoint.getPlace().getCoordinates(),tfEndPoint.getPlace().getCoordinates());
        // validate time
        if (cbTripPeriod.getValue() != TripTimeFrame.LEAVE_NOW) {
            if (LocalDateTime.of(dpDate.getValue(), tpTime.getValue()).compareTo(LocalDateTime.now()) < 0) {
                return;
            }
        }
//        Planner p = new Planner(tfStartPoint.getPlace().getCoordinates(), tfEndPoint.getPlace().getCoordinates());
//        p.setStartTime(
//                cbTripPeriod.getValue() == TripTimeFrame.LEAVE_NOW ?
//                        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) :
//                        LocalDateTime.of(dpDate.getValue(), tpTime.getValue())
//        );
//        PlanPreference pp = new DenvitPlanPreference(Distance.distance(tfStartPoint.getPlace().getCoordinates(), tfEndPoint.getPlace().getCoordinates()));
//        p.setPlanPreference(pp);
//        new Thread(() -> onPlannerComputeFinish(p.getPlans())).start();

        mapController.addDirections(tfStartPoint.getPlace().getCoordinates(), tfEndPoint.getPlace().getCoordinates());
    }

    private void onPlannerComputeFinish(List<Plan> plans) {
        System.out.println("Got routes");
    }
}

