package pt.sensae.services.data.decoder.flow.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import pt.sensae.services.data.decoder.flow.application.DataDecoderNotifierService;
import pt.sensae.services.data.decoder.flow.infrastructure.endpoint.amqp.internal.model.DataDecoderNotificationDTOImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DataDecoderConsumer {

    @Inject
    DataDecoderNotifierService notifier;

    @Inject
    ObjectMapper mapper;

    @Incoming("ingress-data-decoders")
    public CompletionStage<Void> receiveIndexEvent(Message<byte[]> in) throws IOException {
        var dto = mapper.readValue(in.getPayload(), DataDecoderNotificationDTOImpl.class);
        notifier.info(dto);

        return in.ack();
    }
}
