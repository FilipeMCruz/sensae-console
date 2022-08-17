package pt.sensae.services.data.decoder.flow.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sensae.services.data.decoder.flow.domain.DataDecoderRepository;
import pt.sensae.services.data.decoder.flow.domain.SensorTypeId;
import pt.sensae.services.data.decoder.flow.domain.UnHandledDataUnitRepository;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DataUnitHandlerService {

    @Inject
    DataDecoderRepository cache;

    @Inject
    UnHandledDataUnitRepository unhandledSensorDataCache;

    @Inject
    DataUnitProcessor dataPublisher;

    @Inject
    DataUnitNotificationPublisherService notificationPublisher;

    public void publish(MessageConsumed<ObjectNode, SensorRoutingKeys> message) {
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
