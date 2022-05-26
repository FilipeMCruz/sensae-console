package pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.alert.dispatcher.backend.application.RuleScenarioNotifierService;
import pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.internal.model.RuleScenarioNotificationGroupDTOImpl;

import java.io.IOException;

@Service
public class RuleScenarioNotificationConsumer {

    public static final String QUEUE = "internal.alert.dispatcher.queue";

    private final ObjectMapper mapper;

    private final RuleScenarioNotifierService notifier;

    public RuleScenarioNotificationConsumer(RuleScenarioNotifierService notifier, ObjectMapper mapper) {
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveIndexEvent(Message in) throws IOException {
        var dto = mapper.readValue(in.getBody(), RuleScenarioNotificationGroupDTOImpl.class);
        notifier.info(dto);
    }
}
