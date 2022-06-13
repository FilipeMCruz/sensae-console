package pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.slave.backend.application.DataTransformationNotifierService;
import pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.model.DataTransformationNotificationDTOImpl;

import java.io.IOException;

@Service
public class DataTransformationConsumer {

    public static final String QUEUE = "internal.data.processor.slave.queue";

    private final DataTransformationNotifierService notifier;

    private final ObjectMapper mapper;

    public DataTransformationConsumer(DataTransformationNotifierService notifier, ObjectMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), DataTransformationNotificationDTOImpl.class);
        notifier.info(dto);
    }
}
