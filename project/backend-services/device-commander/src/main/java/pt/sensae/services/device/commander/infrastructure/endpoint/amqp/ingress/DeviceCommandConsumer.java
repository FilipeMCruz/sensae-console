package pt.sensae.services.device.commander.infrastructure.endpoint.amqp.ingress;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.sensae.services.device.commander.application.DeviceCommandHandlerService;
import pt.sensae.services.device.commander.infrastructure.endpoint.amqp.ingress.model.DeviceCommandDTOImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DeviceCommandConsumer {

    private final Logger logger = LoggerFactory.getLogger(DeviceCommandConsumer.class);

    @Inject
    DeviceCommandHandlerService handler;

    @Inject
    ObjectMapper mapper;

    @Incoming("ingress-commands")
    public CompletionStage<Void> receiveUpdate(Message<byte[]> in) throws IOException {
        var consumed = mapper.readValue(in.getPayload(), DeviceCommandDTOImpl.class);
        logConsumedMessage(consumed);
        handler.publish(consumed);
        return in.ack();
    }

    private void logConsumedMessage(DeviceCommandDTOImpl in) {
        logger.info("Device Id: {}", in.deviceId.toString());
        logger.info("Device Command: {}", in.commandId);
    }
}
