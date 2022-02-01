package sharespot.services.locationtrackingbackend.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import pt.sharespot.iot.core.sensor.data.MotionDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;

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

    public void save(ProcessedSensorDataWithRecordsDTO data) {
        // if data received has no info about motion try to guess it
        if (!data.data.hasProperty(PropertyName.MOTION)) {
            var lastTenMinutesData = repository.queryPastData(data, TIME_SPAN_IN_MINUTES);

            // if no previous data exists motion status is unknown
            if (lastTenMinutesData.isEmpty()) {
                data.data.withMotion(MotionDataDTO.of("UNKNOWN"));
            } else {
                var moving = Haversine.isMoving(data, lastTenMinutesData, DISTANCE_IN_KM);
                if (moving) {
                    data.data.withMotion(MotionDataDTO.of("ACTIVE"));
                } else {
                    data.data.withMotion(MotionDataDTO.of("INACTIVE"));
                }
            }
        }
        publisher.publish(GPSDataMapper.transform(data));
        repository.insert(data);
    }
}
