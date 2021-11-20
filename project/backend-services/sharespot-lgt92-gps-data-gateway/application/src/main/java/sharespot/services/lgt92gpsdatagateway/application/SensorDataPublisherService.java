package sharespot.services.lgt92gpsdatagateway.application;

import org.springframework.stereotype.Service;
import sharespot.services.lgt92gpsdatagateway.model.SensorData;

@Service
public class SensorDataPublisherService {

    private final EventPublisher sensorDataPublisher;

    public SensorDataPublisherService(EventPublisher sensorDataPublisher) {
        this.sensorDataPublisher = sensorDataPublisher;
    }

    public void registerSensorData(SensorData sensorDataDTO) {
        this.sensorDataPublisher.publish(sensorDataDTO);
    }
}
