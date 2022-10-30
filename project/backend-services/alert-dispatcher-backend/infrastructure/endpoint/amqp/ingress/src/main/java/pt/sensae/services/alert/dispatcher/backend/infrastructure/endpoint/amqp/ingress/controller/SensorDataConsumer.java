package pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.ingress.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.alert.dispatcher.backend.application.RuleEvaluatorService;
import pt.sharespot.iot.core.data.mapper.MessageMapper;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

@Service
public class SensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    public static final String INGRESS_QUEUE = "sensor.alert.dispatcher.queue";

    private final RuleEvaluatorService handler;

    public SensorDataConsumer(RuleEvaluatorService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException {
        var consumed = MessageMapper.toModel(in.getBody());
        logConsumedMessage(consumed);
        handler.insertData(consumed.data);
    }

    private void logConsumedMessage(MessageConsumed<DataUnitDTO, DataRoutingKeys> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
