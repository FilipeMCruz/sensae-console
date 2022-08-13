package pt.sensae.services.data.validator.application;

import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface EventPublisher {
    
    void next(MessageSupplied<SensorDataDTO, SensorRoutingKeys> message);
}
