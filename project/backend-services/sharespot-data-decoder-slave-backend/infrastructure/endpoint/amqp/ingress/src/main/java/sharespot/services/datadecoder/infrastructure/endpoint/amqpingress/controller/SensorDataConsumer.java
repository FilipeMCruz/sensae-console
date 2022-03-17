package sharespot.services.datadecoder.infrastructure.endpoint.amqpingress.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import sharespot.services.datadecoder.application.SensorDataHandlerService;

@Service
public class SensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    public static final String INGRESS_QUEUE = "Sharespot Data Decoder Slave Queue";

    private final SensorDataHandlerService handler;

    public SensorDataConsumer(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(MessageConsumed<ObjectNode> in) {
        logConsumedMessage(in);
        handler.publish(in);
    }

    private void logConsumedMessage(MessageConsumed<ObjectNode> in) {
        logger.info("Data Id Consumer: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
