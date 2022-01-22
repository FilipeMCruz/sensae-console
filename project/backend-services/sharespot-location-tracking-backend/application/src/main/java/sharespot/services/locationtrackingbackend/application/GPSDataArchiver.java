package sharespot.services.locationtrackingbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
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
        repository.insert(data);
        var lastTenMinutesData = repository.queryPastData(data, TIME_SPAN_IN_MINUTES);

        // if no data is presented motion status is unknown
        if (lastTenMinutesData.isEmpty()) {
            publisher.publish(GPSDataMapper.transform(data));
        } else {
            var moving = Haversine.isMoving(data, lastTenMinutesData, DISTANCE_IN_KM);
            publisher.publish(GPSDataMapper.transform(data, moving));
        }
    }
}
