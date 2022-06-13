package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.RoutingKeysProvider;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.application.addressee.service.AddresseeInfoHandler;
import pt.sensae.services.notification.management.backend.application.addressee.service.AddresseeSyncHandler;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AddresseeInfoEmitter implements AddresseeInfoHandler {

    private final AmqpTemplate template;

    private final InternalRoutingKeys infoKeys;

    public AddresseeInfoEmitter(RoutingKeysProvider provider, @Qualifier("amqpTemplate") AmqpTemplate template) {
        this.template = template;
        var infoKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContextType(ContextTypeOptions.ADDRESSEE_CONFIGURATION)
                .withOperationType(OperationTypeOptions.INFO)
                .build();
        if (infoKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.infoKeys = infoKeys.get();
    }

    public void publish(AddresseeDTO addressee) {
        template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, infoKeys.toString(), addressee);
    }
}
