package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpingress.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.lgt92gpsdataprocessor.application.SensorDataHandlerService;
import sharespot.services.lgt92gpsdataprocessor.application.model.MessageConsumed;

@Service
public class SensorDataListener {

    Logger logger = LoggerFactory.getLogger(SensorDataListener.class);

    public static final String INGRESS_QUEUE = "Sharespot LGT92 GPS Data Processor Queue";

    private final SensorDataHandlerService handler;

    public SensorDataListener(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(MessageConsumed<ObjectNode> in) {
        logConsumedMessage(in);
        handler.publish(in);
    }

    private void logConsumedMessage(MessageConsumed<ObjectNode> in) {
        logger.info("Data Consumed: Unknown");
        logger.info("RoutingKeys: {}", in.routingKeys.toString());
    }
}
