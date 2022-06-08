package pt.sensae.services.data.processor.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.domain.DataTransformation;
import pt.sensae.services.data.processor.master.backend.domain.SensorDataTransformationsRepository;

@Service
public class DataTransformationHoarder {

    private final SensorDataTransformationsRepository repository;

    public DataTransformationHoarder(SensorDataTransformationsRepository repository) {
        this.repository = repository;
    }

    public DataTransformation hoard(DataTransformation records) {
        return this.repository.save(records);
    }
}
