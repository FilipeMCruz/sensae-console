package pt.sensae.services.fleet.management.backend.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.data.types.MotionDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sensae.services.fleet.management.backend.domain.SensorDataRepository;

@Service
public class GPSDataArchiver {

    @Value("${sensae.location.heuristic.motion.detection.time}")
    public int TIME_SPAN_IN_MINUTES;

    @Value("${sensae.location.heuristic.motion.detection.distance}")
    public double DISTANCE_IN_KM;

    private final SensorDataRepository repository;
    private final GPSDataPublisher publisher;

    public GPSDataArchiver(SensorDataRepository repository, GPSDataPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public void publish(SensorDataDTO data) {
        // if data received has no info about motion try to guess it
        if (!data.getSensorData().hasProperty(PropertyName.MOTION)) {
            var lastTenMinutesData = repository.queryPastData(data, TIME_SPAN_IN_MINUTES).toList();

            // if no previous data exists motion status is unknown
            if (lastTenMinutesData.isEmpty()) {
                data.getSensorData().withMotion(MotionDataDTO.of("UNKNOWN"));
            } else {
                if (Haversine.isMoving(data, lastTenMinutesData, DISTANCE_IN_KM)) {
                    data.getSensorData().withMotion(MotionDataDTO.of("ACTIVE"));
                } else {
                    data.getSensorData().withMotion(MotionDataDTO.of("INACTIVE"));
                }
            }
        }
        publisher.publish(data);
        repository.insert(data);
    }
}
