package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.GeocodingService;
import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.PlacesAutocompletionService;
import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Place;
import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Prediction;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.Callback;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 *
 * @author Caleb Brinkman
 */
public class AutoCompleteTextField extends JFXTextField implements ChangeListener<String> {
    /**
     * The popup used to select an entry.
     */
    private ContextMenu entriesPopup;
    private TimerTask queryTask;
    private final Timer timer;
    private Place place;
    private LocationGeocodedListener listener;

    /**
     * Construct a new AutoCompleteTextField.
     */
    public AutoCompleteTextField() {
        super();
        timer = new Timer();
        entriesPopup = new ContextMenu();
        textProperty().addListener(this);
        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> Platform.runLater(entriesPopup::hide));
    }


    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<Prediction> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final Prediction result = searchResult.get(i);
            Label entryLabel = new Label(result.getDescription());
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.addEventHandler(ActionEvent.ACTION, (e) -> {
                // Set description as textfield content
                textProperty().removeListener(this);
                Platform.runLater(() -> {
                    setText(result.getDescription());
                    entriesPopup.hide();
                    textProperty().addListener(this);
                });
                // Geocode the location returned by the autocompletion service
                GeocodingService.geocodeAsync(result.getDescription(), places -> {
                    place = places.get(0);
                    listener.locationGeocoded(AutoCompleteTextField.this, place);
                });
            });
            menuItems.add(item);
        }
        Platform.runLater(() -> {
            entriesPopup.getItems().clear();
            entriesPopup.getItems().addAll(menuItems);
        });
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
        if (getText().length() < 3) {
            entriesPopup.hide();
        } else {
            if (queryTask != null) {
                queryTask.cancel();
                timer.purge();
            }
            queryTask = new TimerTask() {
                @Override
                public void run() {
                    PlacesAutocompletionService.autocompleteAsync(s2, predictions -> {
                        if (predictions.size() > 0) {
                            populatePopup(predictions);
                            if (!entriesPopup.isShowing()) {
                                Platform.runLater(() -> entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0));
                            }
                        } else {
                            Platform.runLater(entriesPopup::hide);
                        }
                    });
                }
            };
            timer.schedule(queryTask, 400);

        }
    }

    public void setLocationGeocodedListener(LocationGeocodedListener listener) {
        this.listener = listener;
    }

    interface LocationGeocodedListener {
        public void locationGeocoded(TextField origin, Place value);
    }
}