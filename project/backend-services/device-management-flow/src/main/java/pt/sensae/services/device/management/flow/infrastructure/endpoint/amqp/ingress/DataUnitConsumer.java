package pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.ingress;

import com.google.protobuf.InvalidProtocolBufferException;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.sensae.services.device.management.flow.application.DataUnitHandlerService;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DataUnitConsumer {

    private final Logger logger = LoggerFactory.getLogger(DataUnitConsumer.class);

    @Inject
    DataUnitHandlerService handler;

    @Incoming("ingress-data-units")
    public CompletionStage<Void> receiveUpdate(Message<byte[]> in) throws InvalidProtocolBufferException {
        var consumed = MessageMapper.toModel(in.getPayload());
        logConsumedMessage(consumed);
        handler.publish(consumed);
        return in.ack();
    }

    private void logConsumedMessage(MessageConsumed<SensorDataDTO, SensorRoutingKeys> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
