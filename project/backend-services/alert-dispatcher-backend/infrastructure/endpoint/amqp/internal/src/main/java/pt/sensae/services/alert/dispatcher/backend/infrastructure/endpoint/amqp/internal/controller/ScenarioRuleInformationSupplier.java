package pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.internal.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pt.sensae.services.alert.dispatcher.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import java.nio.charset.StandardCharsets;

@Service
public class ScenarioRuleInformationSupplier {

    private final AmqpTemplate template;
    private final RoutingKeysProvider provider;

    public ScenarioRuleInformationSupplier(AmqpTemplate template, RoutingKeysProvider provider) {
        this.template = template;
        this.provider = provider;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        var info = provider.getInternalBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.RULE_MANAGEMENT)
                .withContextType(ContextTypeOptions.RULE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INIT)
                .build();
        if (info.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        template.send(IoTCoreTopic.INTERNAL_EXCHANGE, info.get()
                .toString(), new Message("{}".getBytes(StandardCharsets.UTF_8)));
    }
}
