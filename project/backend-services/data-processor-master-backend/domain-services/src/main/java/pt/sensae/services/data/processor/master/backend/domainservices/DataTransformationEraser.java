package pt.sensae.services.data.processor.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.domain.SensorDataTransformationsRepository;
import pt.sensae.services.data.processor.master.backend.domain.SensorTypeId;

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
