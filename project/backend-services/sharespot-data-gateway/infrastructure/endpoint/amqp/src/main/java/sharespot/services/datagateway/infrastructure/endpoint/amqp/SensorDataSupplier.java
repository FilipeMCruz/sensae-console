package sharespot.services.datagateway.infrastructure.endpoint.amqp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Controller;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import sharespot.services.datagateway.application.EventPublisher;

@Controller
public class SensorDataSupplier implements EventPublisher {

    Logger logger = LoggerFactory.getLogger(SensorDataSupplier.class);

    private final AmqpTemplate template;

    public SensorDataSupplier(AmqpTemplate template) {
        this.template = template;
    }

    @Override
    public void publish(MessageSupplied<ObjectNode, SensorRoutingKeys> message) {
        template.send(IoTCoreTopic.DATA_EXCHANGE,
                message.routingKeys.toString(),
                new Message(MessageMapper.toUnprocessedBuf(message).toByteArray()));
        logSuppliedMessage(message);
    }

    private void logSuppliedMessage(MessageSupplied<ObjectNode, SensorRoutingKeys> in) {
        logger.info("Data Id Supplied: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
