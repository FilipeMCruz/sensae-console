package pt.sensae.services.data.decoder.slave.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.slave.backend.application.DataDecoderNotifierService;
import pt.sensae.services.data.decoder.slave.backend.infrastructure.endpoint.amqp.internal.model.DataDecoderNotificationDTOImpl;

import java.io.IOException;

@Service
public class DataDecoderConsumer {

    private final DataDecoderNotifierService notifier;

    private final ObjectMapper mapper;

    public DataDecoderConsumer(DataDecoderNotifierService notifier, ObjectMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @RabbitListener(queues = "#{queueNamingService.getDataDecoderQueueName()}")
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DataDecoderNotificationDTOImpl.class);
        notifier.info(dto);
    }
}
