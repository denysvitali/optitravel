package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.response;

import ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model.Prediction;
import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

import java.util.List;

public class PlacesAutocompleteResponse {
    private final List<Prediction> predictions;

    @JsonCreator
    public PlacesAutocompleteResponse(@JsonProperty("predictions") List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }
}
