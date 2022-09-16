package pt.sensae.services.device.ownership.flow.domain;

import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import java.util.Set;

public interface UnHandledDataUnitRepository {

    void insert(MessageConsumed<DataUnitDTO, DataRoutingKeys> data, DeviceId id);

    Set<MessageConsumed<DataUnitDTO, DataRoutingKeys>> retrieve(DeviceId id);
}