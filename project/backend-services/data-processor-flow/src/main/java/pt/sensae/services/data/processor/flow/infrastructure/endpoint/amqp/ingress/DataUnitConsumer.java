package pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.ingress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.protobuf.InvalidProtocolBufferException;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.sensae.services.data.processor.flow.application.DataUnitHandlerService;
import pt.sharespot.iot.core.data.mapper.MessageMapper;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DataUnitConsumer {

    private final Logger logger = LoggerFactory.getLogger(DataUnitConsumer.class);

    @Inject
    DataUnitHandlerService handler;

    @Incoming("ingress-data-units")
    public CompletionStage<Void> receiveUpdate(Message<byte[]> in) throws InvalidProtocolBufferException, JsonProcessingException {
        var consumed = MessageMapper.toUnprocessedModel(in.getPayload());
        logConsumedMessage(consumed);
        handler.publish(consumed);
        return in.ack();
    }

    private void logConsumedMessage(MessageConsumed<ObjectNode, DataRoutingKeys> in) {
        logger.info("Data Id Consumed: {}", in.oid);
        logger.info("RoutingKeys: {}", in.routingKeys.details());
        logger.info("Hops: {}", in.hops);
    }
}
