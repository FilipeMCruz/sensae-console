package pt.sensae.services.device.ownership.flow.application;


import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;

public interface DataUnitPublisher {
    
    void next(MessageSupplied<DataUnitDTO, DataRoutingKeys> message);
}
