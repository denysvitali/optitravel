package ch.supsi.dti.i2b.shrug.optitravel.ui;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.PlacesAutocompleteWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Prediction;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ComboBox;

import java.util.Timer;
import java.util.TimerTask;

public class AutocompleteHandler implements ChangeListener<String>, ListChangeListener<Prediction> {

    private final ComboBox<Prediction> comboBox;
    private TimerTask autocompleteTask;
    private Timer timer;

    public AutocompleteHandler(ComboBox<Prediction> comboBox) {
        this.comboBox = comboBox;
        timer = new Timer();
    }


    /**
     * TextProperty change event populates combobox with values from query to Places Autocomplete APIs.
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // Don't autocomplete if query too short
        if (newValue.length() < 3) {
            comboBox.getItems().clear();
            return;
        }
        // Cancel pending autocomplete queries if any
        if (autocompleteTask != null) {
            System.out.println("delete this");
            autocompleteTask.cancel();
            timer.purge();
        }
        // Create new autocomplete query task
        autocompleteTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Running delayed request with query '" + newValue + "'.");
                PlacesAutocompleteWrapper.getPredictionsAsync(newValue, predictions -> {
                    System.out.println("Received response from request with query '" + newValue + "'.");
                    for (Prediction p : predictions) System.out.println(p);
                    Platform.runLater(() -> {
                        comboBox.getItems().clear();
                        comboBox.getItems().addAll(predictions);
                    });
                });
            }
        };
        // queue autocomplete query task for execution
        System.out.println("Queueing delayed request with query '" + newValue + "'.");
        timer.schedule(autocompleteTask, 400);
    }

    @Override
    public void onChanged(ListChangeListener.Change<? extends Prediction> c) {
        if (comboBox.getItems().size() > 0)
            Platform.runLater(comboBox::show);
        else
            Platform.runLater(comboBox::hide);
    }
}
