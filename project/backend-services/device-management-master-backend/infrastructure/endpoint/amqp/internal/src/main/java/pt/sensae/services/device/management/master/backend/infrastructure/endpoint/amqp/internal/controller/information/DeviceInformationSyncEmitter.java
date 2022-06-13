package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.amqp.internal.controller.information;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationSyncHandler;
import pt.sensae.services.device.management.master.backend.application.DeviceNotificationDTO;
import pt.sensae.services.device.management.master.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeviceInformationSyncEmitter implements DeviceInformationSyncHandler {
    
    private final AmqpTemplate template;

    private final InternalRoutingKeys syncKeys;

    public DeviceInformationSyncEmitter(RoutingKeysProvider provider, @Qualifier("amqpTemplate") AmqpTemplate template) {
        this.template = template;
        var syncKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.SYNC)
                .build();
        if (syncKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.syncKeys = syncKeys.get();
    }


    @Override
    public void publishState(Stream<DeviceNotificationDTO> devices) {
        template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, syncKeys.toString(),
                devices.collect(Collectors.toSet()));
    }
}
