package sharespot.services.lgt92gpsdatagateway.application;

import org.springframework.stereotype.Service;

@Service
public class SensorDataPublisherService {

    private final EventPublisher sensorDataPublisher;

    public SensorDataPublisherService(EventPublisher sensorDataPublisher) {
        this.sensorDataPublisher = sensorDataPublisher;
    }

    public void registerSensorData(Object sensorDataDTO) {
        this.sensorDataPublisher.publish(sensorDataDTO);
    }
}
