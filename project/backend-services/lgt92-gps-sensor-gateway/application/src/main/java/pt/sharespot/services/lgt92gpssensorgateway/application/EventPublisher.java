package pt.sharespot.services.lgt92gpssensorgateway.application;

import pt.sharespot.services.lgt92gpssensorgateway.model.dto.SensorData;

public interface EventPublisher {

    void publish(SensorData eventEmitter);

}
