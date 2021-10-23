package pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.lgt92sensor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LGT92DecodedData {

    @JsonProperty("payload")
    public LGT92PayloadDataDTO payload;

    @JsonProperty("status")
    public String status;
}
