package ch.supsi.dti.i2b.shrug.optitravel.api.GoogleMaps.model;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;

public class StructuredFormatting {
    private final String mainText;
    private final String secondaryText;

    @JsonCreator
    public StructuredFormatting(@JsonProperty("main_text") String mainText,
                                @JsonProperty("secondary_text") String secondaryText) {
        this.mainText = mainText;
        this.secondaryText = secondaryText;
    }

    public String getMainText() {
        return mainText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }
}
