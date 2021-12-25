package sharespot.services.lgt92gpsdatagateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sharespot.iot.core.routing.MessageSupplied;

public interface EventPublisher {

    void publish(MessageSupplied<ObjectNode> eventEmitter);

}
