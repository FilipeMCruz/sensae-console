package pt.sensae.services.data.processor.flow.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import java.util.Set;

public interface UnHandledDataUnitRepository {

    void insert(MessageConsumed<ObjectNode, SensorRoutingKeys> data, SensorTypeId id);

    Set<MessageConsumed<ObjectNode, SensorRoutingKeys>> retrieve(SensorTypeId id);
}
