package pt.sensae.services.device.ownership.flow.application;

import pt.sensae.services.device.ownership.flow.application.model.DeviceTopicMessage;
import pt.sensae.services.device.ownership.flow.domain.DeviceId;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeviceOwnershipNotificationPublisherService {

    private final InternalRoutingKeys pingKeys;

    private final InternalRoutingKeys requestKeys;

    private final DeviceInformationNotificationPublisher publisher;

    public DeviceOwnershipNotificationPublisherService(RoutingKeysProvider provider, DeviceInformationNotificationPublisher publisher) {
        var ping = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.PING)
                .build();

        var unknown = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withOperationType(OperationTypeOptions.UNKNOWN)
                .build();


        if (ping.isEmpty() || unknown.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.pingKeys = ping.get();
        this.requestKeys = unknown.get();
        this.publisher = publisher;
    }

    public void publishRequest(DeviceId device) {
        publisher.next(new DeviceTopicMessage(device, this.requestKeys));
    }

    public void publishPing(DeviceId device) {
        publisher.next(new DeviceTopicMessage(device, this.pingKeys));
    }
}
