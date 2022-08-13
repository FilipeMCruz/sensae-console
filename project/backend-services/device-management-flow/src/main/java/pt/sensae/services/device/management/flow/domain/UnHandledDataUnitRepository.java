package pt.sensae.services.device.management.flow.domain;

import pt.sensae.services.device.management.flow.domain.device.DeviceId;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import java.util.Set;

public interface UnHandledDataUnitRepository {

    void insert(MessageConsumed<SensorDataDTO, SensorRoutingKeys> data, DeviceId id);

    Set<MessageConsumed<SensorDataDTO, SensorRoutingKeys>> retrieve(DeviceId id);
}
