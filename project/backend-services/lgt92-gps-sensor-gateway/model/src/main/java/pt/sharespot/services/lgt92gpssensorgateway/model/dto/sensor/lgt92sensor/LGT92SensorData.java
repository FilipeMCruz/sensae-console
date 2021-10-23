package pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.lgt92sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.SensorData;

public class LGT92SensorData extends SensorData {

    @JsonProperty("decoded")
    public LGT92DecodedData decoded;
}
