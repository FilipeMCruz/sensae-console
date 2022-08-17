package pt.sensae.services.device.commander.application;

import pt.sensae.services.device.commander.application.model.DeviceTopicMessage;
import pt.sensae.services.device.commander.domain.device.Device;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeviceNotificationPublisherService {

    private final InternalRoutingKeys pingKeys;

    private final InternalRoutingKeys requestKeys;

    private final DeviceInformationNotificationPublisher publisher;

    public DeviceNotificationPublisherService(RoutingKeysProvider provider, DeviceInformationNotificationPublisher publisher) {
        var ping = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.PING)
                .build();

        var request = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.REQUEST)
                .build();

        if (ping.isEmpty() || request.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.pingKeys = ping.get();
        this.requestKeys = request.get();
        this.publisher = publisher;
    }

    public void publishRequest(Device device) {
        publisher.next(new DeviceTopicMessage(device, this.requestKeys));
    }

    public void publishPing(Device device) {
        publisher.next(new DeviceTopicMessage(device, this.pingKeys));
    }
}
