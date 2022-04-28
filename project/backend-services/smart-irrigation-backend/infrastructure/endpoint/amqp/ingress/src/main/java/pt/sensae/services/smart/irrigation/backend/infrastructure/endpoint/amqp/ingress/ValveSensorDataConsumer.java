package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.amqp.ingress;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sensae.services.smart.irrigation.backend.application.services.data.DataHandlerService;
import pt.sharespot.iot.core.buf.mapper.MessageMapper;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

@Component
public class ValveSensorDataConsumer {

    Logger logger = LoggerFactory.getLogger(ValveSensorDataConsumer.class);

    public static final String INGRESS_QUEUE = "Sharespot Smart Irrigation Queue (valve)";

    private final DataHandlerService handler;

    public ValveSensorDataConsumer(DataHandlerService handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = INGRESS_QUEUE)
    public void receiveUpdate(Message in) throws InvalidProtocolBufferException {
        var consumed = MessageMapper.toModel(in.getBody());
        logConsumedMessage(consumed);
        handler.handle(consumed.data);
    }

    private void logConsumedMessage(MessageConsumed<ProcessedSensorDataDTO> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}