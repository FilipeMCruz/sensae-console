package pt.sensae.services.data.validator.infrastructure.endpoint.amqp.egress;

import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.jboss.logging.Logger;
import pt.sensae.services.data.validator.application.EventPublisher;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.sensor.mapper.MessageMapper;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataUnitEmitter implements EventPublisher {

    @Channel("egress-data-units")
    Emitter<byte[]> dataUnitEmitter;

    private static final Logger logger = Logger.getLogger(DataUnitEmitter.class);

    @Override
    public void next(MessageSupplied<SensorDataDTO, SensorRoutingKeys> dataUnit) {
        logSuppliedMessage(dataUnit);

        var metadata = Metadata.of(new OutgoingRabbitMQMetadata.Builder()
                .withRoutingKey(dataUnit.routingKeys.toString())
                .build());

        var message = Message.of(MessageMapper.toBuf(dataUnit).toByteArray(), metadata);

        dataUnitEmitter.send(message);
    }

    private void logSuppliedMessage(MessageSupplied<SensorDataDTO, SensorRoutingKeys> in) {
        logger.info("Data Id Supplied: %s".formatted(in.oid));
        logger.info("RoutingKeys: %s".formatted(in.routingKeys.details()));
        logger.info("Hops: %s".formatted(in.hops));
    }
}
