package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.Main;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.*;
import ch.supsi.dti.i2b.shrug.optitravel.models.plan.PlanSegment;
import ch.supsi.dti.i2b.shrug.optitravel.params.DenvitPlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.planner.PlanPreference;
import ch.supsi.dti.i2b.shrug.optitravel.planner.Planner;
import ch.supsi.dti.i2b.shrug.optitravel.utilities.TripTimeFrame;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
    private JFXListView<PlanSegment> lvPlanSegments;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private AnchorPane filtersContainer;

    private MapController mapController;

    private boolean mockedPlanner = true;

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
        lvPlanSegments.setPrefWidth(350);
        mainContainer
			.heightProperty()
			.addListener((observable, oldValue, newValue) ->
				lvPlanSegments.setPrefHeight(mainContainer.getHeight() - filtersContainer.getHeight() + 8 - fabSend.getPrefHeight() / 2)
			);
        filtersContainer
			.heightProperty()
			.addListener((observable, oldValue, newValue) ->
				lvPlanSegments.setPrefHeight(mainContainer.getHeight() - filtersContainer.getHeight() + 8 - fabSend.getPrefHeight() / 2)
			);

        lvPlanSegments.setCellFactory(param -> new PlanSegmentCellItem());

        fabSend.toFront();
        lvPlanSegments.toBack();

        fabSend.setOnAction((event) -> validateAndRequest());
    }

    private void validateAndRequest() {
    	if(!mockedPlanner) {
			if (!tfStartPoint.validate() || !tfEndPoint.validate()) {
				return;
			}
			mapController.fitToBounds(tfStartPoint.getPlace().getCoordinates(), tfEndPoint.getPlace().getCoordinates());
			// validate time
			if (cbTripPeriod.getValue() != TripTimeFrame.LEAVE_NOW) {
				if (LocalDateTime.of(dpDate.getValue(), tpTime.getValue()).compareTo(LocalDateTime.now()) < 0) {
					return;
				}
			}
			Planner p = new Planner(tfStartPoint.getPlace().getCoordinates(), tfEndPoint.getPlace().getCoordinates());
			p.setStartTime(
					cbTripPeriod.getValue() == TripTimeFrame.LEAVE_NOW ?
							LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) :
							LocalDateTime.of(dpDate.getValue(), tpTime.getValue())
			);
			PlanPreference pp = new DenvitPlanPreference(Distance.distance(tfStartPoint.getPlace().getCoordinates(), tfEndPoint.getPlace().getCoordinates()));
			p.setPlanPreference(pp);
			new Thread(() -> onPlannerComputeFinish(p.getPlans())).start();
		} else {
			onPlannerComputeFinish(null);
		}
    }

    private void onPlannerComputeFinish(List<Plan> plans) {
		lvPlanSegments.getItems().clear();
		lvPlanSegments.refresh();

		Plan p = null;
		if(mockedPlanner) {
			List<TimedLocation> timedLocationList = null;
			File f = new File(getClass().getClassLoader()
					.getResource("classdata/path-7.classdata").getFile());
			try {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				timedLocationList = (List<TimedLocation>) ois.readObject();
				System.out.println(timedLocationList);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(timedLocationList != null) {
				p = new Plan(timedLocationList);
			}
		} else {
    		if(plans.size() != 0) {
				p = plans.get(0);
			}
		}

        if(p != null) {
        	List<PlanSegment> planSegments = p.getPlanSegments();
        	Platform.runLater(()->{
        		lvPlanSegments.getItems().clear();
				lvPlanSegments.getItems().addAll(planSegments);
			});

			Coordinate start_coordinate = p.getStartLocation().getCoordinate();
			Coordinate end_coordinate = p.getEndLocation().getCoordinate();

			for (PlanSegment ps : p.getPlanSegments()) {
				if (ps.getTrip() instanceof WaitingTrip || ps.getTrip() instanceof WalkingTrip || ps.getTrip() instanceof ConnectionTrip) {
					Platform.runLater(() -> mapController.addDirections(ps.getStart().getCoordinate(), ps.getEnd().getCoordinate(), TravelModes.WALKING));
				} else {
					Platform.runLater(() -> mapController.addComputedDirections(ps));
					//Platform.runLater(() -> mapController.addDirections(ps.getStart().getCoordinate(), ps.getEnd().getCoordinate(), TravelModes.DRIVING));
				}
			}
			//Platform.runLater(() -> mapController.fitToBounds(start_coordinate, end_coordinate));
		}
    }
}



