package pt.sensae.services.data.validator.application;

import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;

public interface EventPublisher {
    
    void next(MessageSupplied<DataUnitDTO, DataRoutingKeys> message);
}
