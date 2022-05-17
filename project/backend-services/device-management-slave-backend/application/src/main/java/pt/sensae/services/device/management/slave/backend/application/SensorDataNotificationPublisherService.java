package pt.sensae.services.device.management.slave.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Service
public class SensorDataNotificationPublisherService {

    private FluxSink<DeviceTopicMessage> deviceStream;

    private ConnectableFlux<DeviceTopicMessage> devicePublisher;

    private final InternalRoutingKeys pingKeys;

    private final InternalRoutingKeys requestKeys;

    public SensorDataNotificationPublisherService(RoutingKeysProvider provider) {
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
        } else {
            this.pingKeys = ping.get();
            this.requestKeys = request.get();
        }
    }

    @PostConstruct
    public void init() {
        Flux<DeviceTopicMessage> requestPublisher = Flux.create(emitter -> deviceStream = emitter);

        this.devicePublisher = requestPublisher.publish();
        this.devicePublisher.connect();
    }

    public Flux<DeviceTopicMessage> getPublisher() {
        return devicePublisher;
    }

    public void publishRequest(Device device) {
        deviceStream.next(new DeviceTopicMessage(device, this.requestKeys));
    }

    public void publishPing(Device device) {
        deviceStream.next(new DeviceTopicMessage(device, this.pingKeys));
    }
}
