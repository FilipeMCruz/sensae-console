package sharespot.services.locationtrackingbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import pt.sharespot.iot.core.sensor.data.StatusDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;

@Service
public class GPSDataArchiver {

    public static final int TIME_SPAN_IN_MINUTES = 10;
    public static final double DISTANCE_IN_KM = 0.2;

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
                //TODO: fix this in iot core
                data.data = data.data.withStatus(StatusDataDTO.withMotion("UNKNOWN"));
            } else {
                var moving = Haversine.isMoving(data, lastTenMinutesData, DISTANCE_IN_KM);
                if (moving) {
                    data.data = data.data.withStatus(StatusDataDTO.withMotion("ACTIVE"));
                } else {
                    data.data = data.data.withStatus(StatusDataDTO.withMotion("INACTIVE"));
                }
            }
        }
        publisher.publish(GPSDataMapper.transform(data));
        repository.insert(data);
    }
}
