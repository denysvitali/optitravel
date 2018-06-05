package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

public class Prediction {

    private final String description;
    private final StructuredFormatting structuredFormatting;

    @JsonCreator
    public Prediction(@JsonProperty("description") String description,
                      @JsonProperty("structured_formatting") StructuredFormatting structuredFormatting) {
        this.description = description;
        this.structuredFormatting = structuredFormatting;
    }

    public String getDescription() {
        return description;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    @Override
    public String toString() {
        return description;
    }
}
