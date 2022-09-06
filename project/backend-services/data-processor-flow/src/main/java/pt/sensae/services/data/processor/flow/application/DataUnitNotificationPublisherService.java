package pt.sensae.services.data.processor.flow.application;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pt.sensae.services.data.processor.flow.application.model.SensorTypeTopicMessage;
import pt.sensae.services.data.processor.flow.domain.PingRepository;
import pt.sensae.services.data.processor.flow.domain.SensorTypeId;
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

    private final DataDecoderNotificationPublisher publisher;

    private final PingRepository pingRepository;
    
    public DataUnitNotificationPublisherService(RoutingKeysProvider provider, DataDecoderNotificationPublisher publisher, PingRepository pingRepository) {
        var ping = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DATA_PROCESSOR)
                .withContextType(ContextTypeOptions.DATA_PROCESSOR)
                .withOperationType(OperationTypeOptions.PING)
                .build();

        var unknown = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DATA_PROCESSOR)
                .withContextType(ContextTypeOptions.DATA_PROCESSOR)
                .withOperationType(OperationTypeOptions.UNKNOWN)
                .build();

        if (ping.isEmpty() || unknown.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.pingKeys = ping.get();
        this.requestKeys = unknown.get();
        this.publisher = publisher;
        this.pingRepository = pingRepository;
    }

    public void publishRequest(SensorTypeId type) {
        publisher.next(new SensorTypeTopicMessage(type, this.requestKeys));
    }
    
    public void publishPing(SensorTypeId device) {
        if (CACHE_PINGS_OFF_VALUES.contains(cachePings)) {
            publisher.next(new SensorTypeTopicMessage(device, this.pingKeys));
        } else {
            pingRepository.store(device);
        }
    }

    @Scheduled(every = "${sensae.aggregate.pings.duration}")
    public void publishPings() {
        pingRepository.retrieveAll().map(d -> new SensorTypeTopicMessage(d, this.pingKeys)).forEach(publisher::next);
    }
}
