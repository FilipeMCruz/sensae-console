package pt.sensae.services.device.ownership.flow.application;

import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface DataUnitPublisher {
    
    void next(MessageSupplied<SensorDataDTO, SensorRoutingKeys> message);
}
