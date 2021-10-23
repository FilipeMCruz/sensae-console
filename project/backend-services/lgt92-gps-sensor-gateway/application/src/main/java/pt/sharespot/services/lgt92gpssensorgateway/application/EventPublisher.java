package pt.sharespot.services.lgt92gpssensorgateway.application;

import pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.lgt92sensor.LGT92SensorData;

public interface EventPublisher {

    void publish(LGT92SensorData eventEmitter);

}
