package sharespot.services.data.decoder.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sharespot.services.data.decoder.application.DataDecoderNotifierService;
import sharespot.services.data.decoder.infrastructure.endpoint.amqp.internal.model.DataDecoderNotificationDTOImpl;

import java.io.IOException;

@Service
public class DataDecoderConsumer {

    public static final String QUEUE = "internal.data.decoder.slave.queue";

    private final DataDecoderNotifierService notifier;

    private final ObjectMapper mapper;

    public DataDecoderConsumer(DataDecoderNotifierService notifier, ObjectMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DataDecoderNotificationDTOImpl.class);
        notifier.info(dto);
    }
}
