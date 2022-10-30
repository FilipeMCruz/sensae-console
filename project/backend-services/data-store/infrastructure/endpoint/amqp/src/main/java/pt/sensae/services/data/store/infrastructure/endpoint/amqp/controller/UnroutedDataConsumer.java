package pt.sensae.services.data.store.infrastructure.endpoint.amqp.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.store.application.SensorDataHandlerService;
import pt.sharespot.iot.core.data.mapper.MessageMapper;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

@Service
public class UnroutedDataConsumer {

    Logger logger = LoggerFactory.getLogger(UnroutedDataConsumer.class);

    public static final String UNROUTABLE_QUEUE = "sensor.unroutable.store.queue";

    private final SensorDataHandlerService handler;

    public UnroutedDataConsumer(SensorDataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = UNROUTABLE_QUEUE)
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException {
        var consumed = MessageMapper.toModel(in.getBody());
        logConsumedMessage(consumed);
        handler.publish(consumed);
    }

    private void logConsumedMessage(MessageConsumed<DataUnitDTO, DataRoutingKeys> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
