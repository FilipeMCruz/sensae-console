package sharespot.services.lgt92gpsdatagateway.application;

import sharespot.services.lgt92gpsdatagateway.model.SensorData;

public interface EventPublisher {

    void publish(SensorData eventEmitter);

}
