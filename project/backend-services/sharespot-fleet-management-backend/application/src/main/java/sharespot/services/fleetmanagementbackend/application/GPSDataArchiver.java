package sharespot.services.fleetmanagementbackend.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.data.types.MotionDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.fleetmanagementbackend.domain.ProcessedSensorDataRepository;

@Service
public class GPSDataArchiver {

    @Value("${sharespot.location.heuristic.motion.detection.time}")
    public int TIME_SPAN_IN_MINUTES;

    @Value("${sharespot.location.heuristic.motion.detection.distance}")
    public double DISTANCE_IN_KM;

    private final ProcessedSensorDataRepository repository;
    private final GPSDataPublisher publisher;

    public GPSDataArchiver(ProcessedSensorDataRepository repository, GPSDataPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public void publish(ProcessedSensorDataDTO data) {
        // if data received has no info about motion try to guess it
        if (!data.getSensorData().hasProperty(PropertyName.MOTION)) {
            var lastTenMinutesData = repository.queryPastData(data, TIME_SPAN_IN_MINUTES).toList();

            // if no previous data exists motion status is unknown
            if (lastTenMinutesData.isEmpty()) {
                data.getSensorData().withMotion(MotionDataDTO.of("UNKNOWN"));
            } else {
                var moving = Haversine.isMoving(data, lastTenMinutesData, DISTANCE_IN_KM);
                if (moving) {
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
