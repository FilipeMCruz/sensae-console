package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress.data;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sensae.services.smart.irrigation.backend.application.services.data.DataHandlerService;
import pt.sharespot.iot.core.data.mapper.MessageMapper;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.RoutingKeys;

@Component
public class StoveSensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(StoveSensorDataConsumer.class);

    public static final String INGRESS_QUEUE = "sensor.smart.irrigation.stove.queue";

    private final DataHandlerService handler;

    public StoveSensorDataConsumer(DataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException {
        var consumed = MessageMapper.toModel(in.getBody());
        logConsumedMessage(consumed);
        handler.handle(consumed.data);
    }

    private void logConsumedMessage(MessageConsumed<DataUnitDTO, DataRoutingKeys> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
