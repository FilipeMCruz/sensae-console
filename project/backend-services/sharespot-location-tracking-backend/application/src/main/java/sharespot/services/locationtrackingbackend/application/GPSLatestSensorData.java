package sharespot.services.locationtrackingbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;

import java.util.List;

@Service
public class GPSLatestSensorData {

    private final ProcessedSensorDataRepository repository;

    public GPSLatestSensorData(ProcessedSensorDataRepository repository) {
        this.repository = repository;
    }

    public List<ProcessedSensorDataWithRecordsDTO> latest() {
        return repository.lastDataOfEachDevice();
    }
}
