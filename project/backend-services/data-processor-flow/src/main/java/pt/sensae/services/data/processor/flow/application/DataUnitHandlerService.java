package pt.sensae.services.data.processor.flow.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sensae.services.data.processor.flow.domain.DataProcessorRepository;
import pt.sensae.services.data.processor.flow.domain.SensorTypeId;
import pt.sensae.services.data.processor.flow.domain.UnHandledDataUnitRepository;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DataUnitHandlerService {

    @Inject
    DataProcessorRepository cache;

    @Inject
    UnHandledDataUnitRepository unhandledSensorDataCache;

    @Inject
    DataUnitProcessor dataPublisher;

    @Inject
    DataUnitNotificationPublisherService notificationPublisher;

    public void publish(MessageConsumed<ObjectNode, DataRoutingKeys> message) {
        var type = SensorTypeId.of(message.routingKeys.sensorTypeId.details());
        if (cache.findById(type).isPresent()) {
            dataPublisher.publish(message);
            notificationPublisher.publishPing(type);
        } else {
            unhandledSensorDataCache.insert(message, type);
            notificationPublisher.publishRequest(type);
        }
    }
}
