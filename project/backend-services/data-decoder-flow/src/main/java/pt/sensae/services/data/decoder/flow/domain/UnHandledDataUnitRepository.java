package pt.sensae.services.data.decoder.flow.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import java.util.Set;

public interface UnHandledDataUnitRepository {

    void insert(MessageConsumed<ObjectNode, DataRoutingKeys> data, SensorTypeId id);

    Set<MessageConsumed<ObjectNode, DataRoutingKeys>> retrieve(SensorTypeId id);
}
