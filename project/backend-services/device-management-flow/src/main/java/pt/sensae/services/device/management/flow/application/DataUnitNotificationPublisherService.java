package pt.sensae.services.device.management.flow.application;

import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.sensae.services.device.management.flow.application.model.DeviceTopicMessage;
import pt.sensae.services.device.management.flow.domain.PingRepository;
import pt.sensae.services.device.management.flow.domain.device.Device;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class DataUnitNotificationPublisherService {

    private static final Set<String> CACHE_PINGS_OFF_VALUES = Set.of("off", "disabled");

    @ConfigProperty(name = "sensae.aggregate.pings.duration")
    String cachePings;

    private final InternalRoutingKeys pingKeys;

    private final InternalRoutingKeys requestKeys;

    private final DeviceInformationNotificationPublisher publisher;

    private final PingRepository pingRepository;

    public DataUnitNotificationPublisherService(RoutingKeysProvider provider, DeviceInformationNotificationPublisher publisher, PingRepository pingRepository) {
        var ping = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_INFORMATION)
                .withOperationType(OperationTypeOptions.PING)
                .build();

        var request = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withContextType(ContextTypeOptions.DEVICE_INFORMATION)
                .withOperationType(OperationTypeOptions.UNKNOWN)
                .build();

        if (ping.isEmpty() || request.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.pingKeys = ping.get();
        this.requestKeys = request.get();
        this.publisher = publisher;
        this.pingRepository = pingRepository;
    }

    public void publishRequest(Device device) {
        publisher.next(new DeviceTopicMessage(device, this.requestKeys));
    }

    public void publishPing(Device device) {
        if (CACHE_PINGS_OFF_VALUES.contains(cachePings)) {
            publisher.next(new DeviceTopicMessage(device, this.pingKeys));
        } else {
            pingRepository.store(device);
        }
    }

    @Scheduled(every = "${sensae.aggregate.pings.duration}")
    public void publishPings() {
        pingRepository.retrieveAll().map(d -> new DeviceTopicMessage(d, this.pingKeys)).forEach(publisher::next);
    }
}
