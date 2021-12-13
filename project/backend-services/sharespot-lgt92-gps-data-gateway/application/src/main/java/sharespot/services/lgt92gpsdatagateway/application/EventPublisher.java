package sharespot.services.lgt92gpsdatagateway.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import sharespot.services.lgt92gpsdatagateway.application.model.MessageSupplied;

public interface EventPublisher {

    void publish(MessageSupplied<ObjectNode> eventEmitter);

}
