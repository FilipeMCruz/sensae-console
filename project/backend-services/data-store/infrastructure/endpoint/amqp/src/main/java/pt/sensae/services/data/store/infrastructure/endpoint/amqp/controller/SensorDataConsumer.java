package pt.sensae.services.data.store.infrastructure.endpoint.amqp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.data.mapper.MessageMapper;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sensae.services.data.store.application.SensorDataHandlerService;

@Service
public class SensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    public static final String QUEUE = "sensor.data.store.queue";

    private final SensorDataHandlerService handler;

    public SensorDataConsumer(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException, JsonProcessingException {
        var consumed = MessageMapper.toUnprocessedModel(in.getBody());
        logConsumedMessage(consumed);
        handler.publish(consumed);
    }

    private void logConsumedMessage(MessageConsumed<ObjectNode, DataRoutingKeys> in) {
        logger.info("Data Consumed: Unknown");
        logger.info("RoutingKeys: {}", in.routingKeys.details());
    }
}
