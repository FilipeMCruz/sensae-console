package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor.lgt92sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.sharespot.services.lgt92gpssensorprocessor.application.InSensorDataDTO;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor.SensorData;

public class LGT92SensorData extends SensorData implements InSensorDataDTO {

    @JsonProperty("decoded")
    public LGT92DecodedData decoded;
}
