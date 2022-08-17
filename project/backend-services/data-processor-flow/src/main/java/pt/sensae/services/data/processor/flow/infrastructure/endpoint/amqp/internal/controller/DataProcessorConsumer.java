package pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import pt.sensae.services.data.processor.flow.application.DataTransformationNotifierService;
import pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.internal.model.DataTransformationNotificationDTOImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DataProcessorConsumer {

    @Inject
    DataTransformationNotifierService notifier;

    @Inject
    ObjectMapper mapper;

    @Incoming("ingress-data-processors")
    public CompletionStage<Void> receiveIndexEvent(Message<byte[]> in) throws IOException {
        var dto = mapper.readValue(in.getPayload(), DataTransformationNotificationDTOImpl.class);
        notifier.info(dto);

        return in.ack();
    }
}
