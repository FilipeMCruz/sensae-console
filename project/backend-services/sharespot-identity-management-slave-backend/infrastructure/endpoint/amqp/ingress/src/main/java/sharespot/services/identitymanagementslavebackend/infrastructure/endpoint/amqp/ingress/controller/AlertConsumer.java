package sharespot.services.identitymanagementslavebackend.infrastructure.endpoint.amqp.ingress.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import sharespot.services.identitymanagementslavebackend.application.AlertHandlerService;

import java.io.IOException;

@Service
public class AlertConsumer {

    public static final String QUEUE = "alert.identity.management.slave.queue";

    private final ObjectMapper mapper;

    public final AlertHandlerService handler;

    public AlertConsumer(ObjectMapper mapper, AlertHandlerService handler) {
        this.mapper = mapper;
        this.handler = handler;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) throws IOException {
        var consumed = mapper.readValue(in.getBody(), AlertDTO.class);
        handler.info(consumed);
    }
}
