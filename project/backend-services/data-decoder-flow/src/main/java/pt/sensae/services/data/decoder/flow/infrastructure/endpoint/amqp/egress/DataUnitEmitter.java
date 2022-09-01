package pt.sensae.services.data.decoder.flow.infrastructure.endpoint.amqp.egress;

import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.jboss.logging.Logger;
import pt.sensae.services.data.decoder.flow.application.DataUnitPublisher;
import pt.sharespot.iot.core.data.mapper.MessageMapper;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.keys.RoutingKeys;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataUnitEmitter implements DataUnitPublisher {

    @Channel("egress-data-units")
    Emitter<byte[]> dataUnitEmitter;

    private static final Logger logger = Logger.getLogger(DataUnitEmitter.class);

    @Override
    public void next(MessageSupplied<DataUnitDTO, DataRoutingKeys> dataUnit) {
        logSuppliedMessage(dataUnit);

        var metadata = Metadata.of(new OutgoingRabbitMQMetadata.Builder()
                .withRoutingKey(dataUnit.routingKeys.toString())
                .build());

        var message = Message.of(MessageMapper.toBuf(dataUnit).toByteArray(), metadata);

        dataUnitEmitter.send(message);
    }

    private void logSuppliedMessage(MessageSupplied<DataUnitDTO, DataRoutingKeys> in) {
        logger.info("Data Id Supplied: %s".formatted(in.oid));
        logger.info("RoutingKeys: %s".formatted(in.routingKeys.details()));
        logger.info("Hops: %s".formatted(in.hops));
    }
}
