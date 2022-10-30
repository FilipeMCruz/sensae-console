package pt.sensae.services.data.gateway.infrastructure.endpoint.amqp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.jboss.logging.Logger;
import pt.sensae.services.data.gateway.application.EventPublisher;
import pt.sharespot.iot.core.data.mapper.MessageMapper;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataUnitEmitter implements EventPublisher {

    @Channel("data-units")
    Emitter<byte[]> dataUnitEmitter;

    private static final Logger logger = Logger.getLogger(DataUnitEmitter.class);

    @Override
    public void publish(MessageSupplied<ObjectNode, DataRoutingKeys> dataUnit) {
        logSuppliedMessage(dataUnit);

        var metadata = Metadata.of(new OutgoingRabbitMQMetadata.Builder()
                .withRoutingKey(dataUnit.routingKeys.toString())
                .build());

        var message = Message.of(MessageMapper.toUnprocessedBuf(dataUnit).toByteArray(), metadata);
        
        dataUnitEmitter.send(message);
    }

    private void logSuppliedMessage(MessageSupplied<ObjectNode, DataRoutingKeys> in) {
        logger.info("Data Id Supplied: %s".formatted(in.oid));
        logger.info("RoutingKeys: %s".formatted(in.routingKeys.details()));
        logger.info("Hops: %s".formatted(in.hops));
    }
}
