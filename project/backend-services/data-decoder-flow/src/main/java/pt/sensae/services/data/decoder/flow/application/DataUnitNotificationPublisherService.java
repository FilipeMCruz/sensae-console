package pt.sensae.services.data.decoder.flow.application;

import pt.sensae.services.data.decoder.flow.application.model.SensorTypeTopicMessage;
import pt.sensae.services.data.decoder.flow.domain.SensorTypeId;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataUnitNotificationPublisherService {

    private final InternalRoutingKeys pingKeys;

    private final InternalRoutingKeys requestKeys;

    private final DataDecoderNotificationPublisher publisher;

    public DataUnitNotificationPublisherService(RoutingKeysProvider provider, DataDecoderNotificationPublisher publisher) {
        var ping = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.PING)
                .build();

        var unknown = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.UNKNOWN)
                .build();

        if (ping.isEmpty() || unknown.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        this.pingKeys = ping.get();
        this.requestKeys = unknown.get();
        this.publisher = publisher;
    }

    public void publishRequest(SensorTypeId type) {
        publisher.next(new SensorTypeTopicMessage(type, this.requestKeys));
    }

    public void publishPing(SensorTypeId type) {
        publisher.next(new SensorTypeTopicMessage(type, this.pingKeys));
    }
}
