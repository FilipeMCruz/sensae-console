package pt.sharespot.services.lgt92gpssensorgateway.application;

import org.springframework.stereotype.Component;
import pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.lgt92sensor.LGT92SensorData;

@Component
public class LGT92SensorDataPublisherService {

    private final EventPublisher sensorDataPublisher;

    public LGT92SensorDataPublisherService(EventPublisher sensorDataPublisher) {
        this.sensorDataPublisher = sensorDataPublisher;
    }

    public void registerSensorData(LGT92SensorData sensorDataDTO) {
        this.sensorDataPublisher.publish(sensorDataDTO);
    }
}
