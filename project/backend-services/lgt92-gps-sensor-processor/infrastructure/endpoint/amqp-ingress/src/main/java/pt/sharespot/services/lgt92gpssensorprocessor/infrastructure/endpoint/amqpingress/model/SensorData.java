package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.sharespot.services.lgt92gpssensorprocessor.application.InSensorDataDTO;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor.AbstractSensorData;
import pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpingress.model.sensor.lgt92sensor.LGT92DecodedData;

public class SensorData extends AbstractSensorData implements InSensorDataDTO {

    @JsonProperty("decoded")
    public LGT92DecodedData decoded;
}
