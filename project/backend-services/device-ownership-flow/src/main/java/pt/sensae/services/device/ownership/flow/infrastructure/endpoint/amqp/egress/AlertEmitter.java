package pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.egress;

import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.jboss.logging.Logger;
import pt.sensae.services.device.ownership.flow.application.AlertPublisher;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AlertEmitter implements AlertPublisher {

    @Channel("egress-alerts")
    Emitter<MessageSupplied<AlertDTO, AlertRoutingKeys>> dataUnitEmitter;

    private static final Logger logger = Logger.getLogger(AlertEmitter.class);

    @Override
    public void next(MessageSupplied<AlertDTO, AlertRoutingKeys> alert) {
        logSuppliedMessage(alert);

        var metadata = Metadata.of(new OutgoingRabbitMQMetadata.Builder()
                .withRoutingKey(alert.routingKeys.toString())
                .build());

        var message = Message.of(alert, metadata);

        dataUnitEmitter.send(message);
    }

    private void logSuppliedMessage(MessageSupplied<AlertDTO, AlertRoutingKeys> in) {
        logger.info("Data Id Supplied: %s".formatted(in.oid));
        logger.info("RoutingKeys: %s".formatted(in.routingKeys.details()));
        logger.info("Hops: %s".formatted(in.hops));
    }
}
