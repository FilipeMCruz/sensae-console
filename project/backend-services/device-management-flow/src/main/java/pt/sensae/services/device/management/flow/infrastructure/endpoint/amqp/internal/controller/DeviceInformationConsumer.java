package pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import pt.sensae.services.device.management.flow.application.DeviceInformationNotifierService;
import pt.sensae.services.device.management.flow.infrastructure.endpoint.amqp.internal.model.DeviceNotificationDTOImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DeviceInformationConsumer {

    @Inject
    DeviceInformationNotifierService notifier;

    @Inject
    ObjectMapper mapper;

    @Incoming("ingress-device-informations")
    public CompletionStage<Void> receiveIndexEvent(Message<byte[]> in) throws IOException {
        var dto = mapper.readValue(in.getPayload(), DeviceNotificationDTOImpl.class);
        notifier.info(dto);

        return in.ack();
    }
}
