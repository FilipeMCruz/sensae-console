package pt.sensae.services.data.decoder.slave.backend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import pt.sensae.services.data.decoder.slave.backend.domain.SensorTypeId;

import javax.annotation.PostConstruct;

@Service
public class SensorDataNotificationPublisherService {

    private FluxSink<SensorTypeTopicMessage> deviceStream;

    private ConnectableFlux<SensorTypeTopicMessage> devicePublisher;

    private final InternalRoutingKeys pingKeys;

    private final InternalRoutingKeys requestKeys;

    public SensorDataNotificationPublisherService(RoutingKeysProvider provider) {
        var ping = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.PING)
                .build();

        var request = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
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
        Flux<SensorTypeTopicMessage> requestPublisher = Flux.create(emitter -> deviceStream = emitter);

        this.devicePublisher = requestPublisher.publish();
        this.devicePublisher.connect();
    }

    public Flux<SensorTypeTopicMessage> getPublisher() {
        return devicePublisher;
    }

    public void publishRequest(SensorTypeId type) {
        deviceStream.next(new SensorTypeTopicMessage(type, this.requestKeys));
    }

    public void publishPing(SensorTypeId type) {
        deviceStream.next(new SensorTypeTopicMessage(type, this.pingKeys));
    }
}
