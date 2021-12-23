package sharespot.services.dataprocessormaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.domain.SensorDataTransformationsRepository;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;

@Service
public class DataTransformationEraser {

    private final SensorDataTransformationsRepository repository;

    public DataTransformationEraser(SensorDataTransformationsRepository repository) {
        this.repository = repository;
    }

    public SensorTypeId erase(SensorTypeId deviceId) {
        return repository.delete(deviceId);
    }
}
