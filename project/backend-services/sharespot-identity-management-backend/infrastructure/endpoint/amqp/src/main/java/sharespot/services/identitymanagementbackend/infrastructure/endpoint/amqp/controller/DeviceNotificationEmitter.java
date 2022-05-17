package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import sharespot.services.identitymanagementbackend.application.RoutingKeysProvider;
import sharespot.services.identitymanagementbackend.application.internal.DeviceInformationEventHandlerService;

@Component
public class DeviceNotificationEmitter {

    public DeviceNotificationEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, DeviceInformationEventHandlerService service, RoutingKeysProvider provider) {
        var info = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
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
