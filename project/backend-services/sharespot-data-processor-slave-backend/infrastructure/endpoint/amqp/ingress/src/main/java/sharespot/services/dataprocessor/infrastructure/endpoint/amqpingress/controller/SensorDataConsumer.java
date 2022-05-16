package sharespot.services.dataprocessor.infrastructure.endpoint.amqpingress.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import sharespot.services.dataprocessor.application.SensorDataHandlerService;

@Service
public class SensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    public static final String INGRESS_QUEUE = "Sharespot Data Processor Slave Queue";

    private final SensorDataHandlerService handler;

    public SensorDataConsumer(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException, JsonProcessingException {
        var consumed = MessageMapper.toUnprocessedModel(in.getBody());
        logConsumedMessage(consumed);
        handler.publish(consumed);
    }

    private void logConsumedMessage(MessageConsumed<ObjectNode> in) {
        logger.info("Data Id Consumer: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
