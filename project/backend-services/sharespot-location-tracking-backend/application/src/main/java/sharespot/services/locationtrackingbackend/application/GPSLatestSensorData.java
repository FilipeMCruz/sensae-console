package sharespot.services.locationtrackingbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.locationtrackingbackend.domain.model.livedata.SensorData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GPSLatestSensorData {

    private final ProcessedSensorDataRepository repository;

    public GPSLatestSensorData(ProcessedSensorDataRepository repository) {
        this.repository = repository;
    }

    public List<SensorData> latest() {
        return repository.lastDataOfEachDevice()
                .stream()
                .map(GPSDataMapper::transform)
                .collect(Collectors.toList());
    }
}
