package sharespot.services.datagateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface EventPublisher {

    void publish(MessageSupplied<ObjectNode, SensorRoutingKeys> eventEmitter);

}
