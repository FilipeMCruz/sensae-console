package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.controller.device;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.IoTCoreTopic;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.identity.management.backend.application.RoutingKeysProvider;
import pt.sensae.services.identity.management.backend.application.internal.device.DeviceInformationEventHandlerService;

import javax.annotation.PostConstruct;

@Component
public class DeviceIdentityInfoEmitter {

    private final AmqpTemplate template;

    private final DeviceInformationEventHandlerService service;

    private final InternalRoutingKeys info;

    public DeviceIdentityInfoEmitter(@Qualifier("amqpTemplate") AmqpTemplate template, DeviceInformationEventHandlerService service, RoutingKeysProvider provider) {
        this.template = template;
        this.service = service;
        var info = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.INFO)
                .build();
        if (info.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.info = info.get();
    }

    @PostConstruct
    private void init() {
        service.getSinglePublisher()
                .subscribe(outData -> template.convertAndSend(IoTCoreTopic.INTERNAL_EXCHANGE, info
                        .toString(), outData));
    }
}
