package sharespot.services.dataprocessor.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import sharespot.services.dataprocessor.domain.SensorTypeId;
import sharespot.services.dataprocessor.domainservices.DataTransformationCache;
import sharespot.services.dataprocessor.domainservices.UnhandledSensorDataCache;

@Service
public class SensorDataHandlerService {

    private final DataTransformationCache cache;

    private final UnhandledSensorDataCache unhandledSensorDataCache;

    private final SensorDataPublisherService dataPublisher;

    private final SensorDataNotificationPublisherService notificationPublisher;

    public SensorDataHandlerService(DataTransformationCache cache,
                                    UnhandledSensorDataCache unhandledSensorDataCache,
                                    SensorDataPublisherService handler,
                                    SensorDataNotificationPublisherService notificationPublisher) {
        this.cache = cache;
        this.unhandledSensorDataCache = unhandledSensorDataCache;
        this.dataPublisher = handler;
        this.notificationPublisher = notificationPublisher;
    }

    public void info(MessageConsumed<ObjectNode, SensorRoutingKeys> message) {
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
