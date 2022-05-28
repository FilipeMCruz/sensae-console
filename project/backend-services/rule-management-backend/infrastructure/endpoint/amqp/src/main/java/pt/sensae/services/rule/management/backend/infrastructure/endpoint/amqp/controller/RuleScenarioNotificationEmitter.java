package pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sensae.services.rule.management.backend.application.RoutingKeysProvider;
import pt.sensae.services.rule.management.backend.application.RuleScenarioHandlerService;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.annotation.PostConstruct;

@Component
public class RuleScenarioNotificationEmitter {

    private final AmqpTemplate template;
    private final RuleScenarioHandlerService service;

    private final InternalRoutingKeys syncKeys;

    private final InternalRoutingKeys infoKeys;

    public RuleScenarioNotificationEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, RuleScenarioHandlerService service, RoutingKeysProvider provider) {
        this.template = template;
        this.service = service;
        var syncKeys = provider.getInternalBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.RULE_MANAGEMENT)
                .withContextType(ContextTypeOptions.RULE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.SYNC)
                .build();
        if (syncKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.syncKeys = syncKeys.get();
        var infoKeys = provider.getInternalBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.RULE_MANAGEMENT)
                .withContextType(ContextTypeOptions.RULE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .build();
        if (infoKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.infoKeys = infoKeys.get();
    }

    @PostConstruct
    public void init() {
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, outData.isStale() ? syncKeys.toString() : infoKeys.toString(), outData));
    }
}
