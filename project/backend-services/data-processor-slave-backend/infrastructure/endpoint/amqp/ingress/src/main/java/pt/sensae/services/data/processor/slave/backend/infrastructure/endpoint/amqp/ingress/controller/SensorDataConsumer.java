package pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.ingress.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sensae.services.data.processor.slave.backend.application.SensorDataHandlerService;

@Service
public class SensorDataConsumer {

    private final Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    private final SensorDataHandlerService handler;

    public SensorDataConsumer(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = "#{queueNamingService.getDataQueueName()}")
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException, JsonProcessingException {
        var consumed = MessageMapper.toUnprocessedModel(in.getBody());
        logConsumedMessage(consumed);
        handler.info(consumed);
    }

    private void logConsumedMessage(MessageConsumed<ObjectNode, SensorRoutingKeys> in) {
        logger.info("Data Id Consumer: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
