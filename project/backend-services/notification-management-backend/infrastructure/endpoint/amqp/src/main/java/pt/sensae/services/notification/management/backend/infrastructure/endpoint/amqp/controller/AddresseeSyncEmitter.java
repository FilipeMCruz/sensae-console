package pt.sensae.services.notification.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.RoutingKeysProvider;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.application.addressee.service.AddresseeSyncHandler;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AddresseeSyncEmitter implements AddresseeSyncHandler {

    private final AmqpTemplate template;

    private final InternalRoutingKeys syncKeys;

    public AddresseeSyncEmitter(RoutingKeysProvider provider, @Qualifier("amqpTemplate") AmqpTemplate template) {
        this.template = template;
        var syncKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContextType(ContextTypeOptions.ADDRESSEE_CONFIGURATION)
                .withOperationType(OperationTypeOptions.SYNC)
                .build();
        if (syncKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.syncKeys = syncKeys.get();
    }


    @Override
    public void publishState(Stream<AddresseeDTO> addressees) {
        template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, syncKeys.toString(),
                addressees.collect(Collectors.toSet()));
    }
}
