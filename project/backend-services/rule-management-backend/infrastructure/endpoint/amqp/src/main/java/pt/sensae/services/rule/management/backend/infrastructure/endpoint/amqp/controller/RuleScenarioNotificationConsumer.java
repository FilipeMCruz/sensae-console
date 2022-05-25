package pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.sensae.services.rule.management.backend.application.RuleScenarioDraftApplierService;

@Component
public class RuleScenarioNotificationConsumer {

    public static final String QUEUE = "internal.rule.management.queue";

    private final RuleScenarioDraftApplierService service;

    public RuleScenarioNotificationConsumer(RuleScenarioDraftApplierService service) {
        this.service = service;
    }

    @RabbitListener(queues = QUEUE)
    public void receiveUpdate(Message in) {
        this.service.publishCurrentState();
    }
}
