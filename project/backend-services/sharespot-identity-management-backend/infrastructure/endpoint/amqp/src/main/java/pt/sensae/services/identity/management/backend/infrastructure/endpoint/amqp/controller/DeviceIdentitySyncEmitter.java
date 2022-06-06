package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.identity.management.backend.application.RoutingKeysProvider;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceIdentitySyncHandler;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceIdentityDTO;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeviceIdentitySyncEmitter implements DeviceIdentitySyncHandler {
    
    private final AmqpTemplate template;

    private final InternalRoutingKeys syncKeys;

    public DeviceIdentitySyncEmitter(RoutingKeysProvider provider, @Qualifier("amqpTemplate") AmqpTemplate template) {
        this.template = template;
        var syncKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.SYNC)
                .build();
        if (syncKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.syncKeys = syncKeys.get();
    }


    @Override
    public void publishState(Stream<DeviceIdentityDTO> devices) {
        template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, syncKeys.toString(),
                devices.collect(Collectors.toSet()));
    }
}
