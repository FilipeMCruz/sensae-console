package pt.sensae.services.rule.management.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sensae.services.rule.management.application.RoutingKeysProvider;
import pt.sensae.services.rule.management.application.RuleScenarioHandlerService;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

@Component
public class RuleScenarioNotificationEmitter {

    public RuleScenarioNotificationEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, RuleScenarioHandlerService service, RoutingKeysProvider provider) {
        var info = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.RULE_MANAGEMENT)
                .withContextType(ContextTypeOptions.RULE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .build();
        if (info.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, info.get()
                        .toString(), outData));
    }
}
