package pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.ingress;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.sensae.services.device.ownership.flow.application.AlertHandlerService;
import pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class AlertConsumer {

    private final Logger logger = LoggerFactory.getLogger(AlertConsumer.class);

    @Inject
    AlertHandlerService handler;

    @Inject
    ObjectMapper mapper;

    @Incoming("ingress-alerts")
    public CompletionStage<Void> receiveUpdate(Message<byte[]> in) throws IOException {
        var consumed = mapper.readValue(in.getPayload(), new TypeReference<MessageConsumed<AlertDTO, AlertRoutingKeys>>() {
        });
        logConsumedMessage(consumed);
        handler.info(consumed);
        return in.ack();
    }

    private void logConsumedMessage(MessageConsumed<AlertDTO, AlertRoutingKeys> in) {
        logger.info("Alert Id Consumer: {}", in.data.id);
        logger.info("Category: {}", in.data.category);
        logger.info("Sub Category: {}", in.data.subCategory);
    }
}
