package sharespot.services.data.decoder.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import sharespot.services.data.decoder.domain.SensorTypeId;
import sharespot.services.datadecoder.domainservices.DataDecoderCache;
import sharespot.services.datadecoder.domainservices.UnhandledSensorDataCache;

@Service
public class SensorDataHandlerService {

    private final DataDecoderCache cache;

    private final UnhandledSensorDataCache unhandledSensorDataCache;

    private final SensorDataPublisherService dataPublisher;

    private final SensorDataNotificationPublisherService notificationPublisher;

    public SensorDataHandlerService(DataDecoderCache cache,
                                    UnhandledSensorDataCache unhandledSensorDataCache,
                                    SensorDataPublisherService handler,
                                    SensorDataNotificationPublisherService notificationPublisher) {
        this.cache = cache;
        this.unhandledSensorDataCache = unhandledSensorDataCache;
        this.dataPublisher = handler;
        this.notificationPublisher = notificationPublisher;
    }

    public void info(MessageConsumed<ObjectNode> message) {
        var type = SensorTypeId.of(message.routingKeys.sensorTypeId);
        if (cache.findById(type).isPresent()) {
            dataPublisher.publish(message);
            notificationPublisher.publishPing(type);
        } else {
            unhandledSensorDataCache.insert(message, type);
            notificationPublisher.publishRequest(type);
        }
    }
}
