package pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import pt.sensae.services.device.ownership.flow.application.DeviceOwnershipNotifierService;
import pt.sensae.services.device.ownership.flow.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DeviceOwnershipConsumer {

    @Inject
    DeviceOwnershipNotifierService notifier;

    @Inject
    ObjectMapper mapper;

    @Incoming("ingress-device-ownership")
    public CompletionStage<Void> receiveIndexEvent(Message<byte[]> in) throws IOException {
        var dto = mapper.readValue(in.getPayload(), DeviceNotificationDTOImpl.class);
        notifier.info(dto);

        return in.ack();
    }
}
