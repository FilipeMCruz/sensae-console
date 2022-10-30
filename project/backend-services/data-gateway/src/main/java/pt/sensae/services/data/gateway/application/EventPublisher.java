package pt.sensae.services.data.gateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;

public interface EventPublisher {

    void publish(MessageSupplied<ObjectNode, DataRoutingKeys> dataUnit);

}
