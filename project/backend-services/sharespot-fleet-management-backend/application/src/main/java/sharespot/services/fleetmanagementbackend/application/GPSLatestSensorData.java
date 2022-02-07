package sharespot.services.fleetmanagementbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.fleetmanagementbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.SensorData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GPSLatestSensorData {

    private final ProcessedSensorDataRepository repository;

    public GPSLatestSensorData(ProcessedSensorDataRepository repository) {
        this.repository = repository;
    }

    public List<SensorData> latest(List<String> devices) {
        return repository.lastDataOfEachDevice()
                .stream()
                .map(GPSDataMapper::transform)
                .filter(s -> devices.contains(s.device().id().toString()))
                .collect(Collectors.toList());
    }

    public List<SensorData> latest() {
        return repository.lastDataOfEachDevice()
                .stream()
                .map(GPSDataMapper::transform)
                .collect(Collectors.toList());
    }
}
