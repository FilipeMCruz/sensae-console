package sharespot.services.datagateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sharespot.iot.core.sensor.routing.MessageSupplied;

public interface EventPublisher {

    void publish(MessageSupplied<ObjectNode> eventEmitter);

}
