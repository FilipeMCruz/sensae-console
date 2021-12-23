package sharespot.services.dataprocessormaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorDataTransformationsRepository;

@Service
public class DataTransformationHoarder {

    private final SensorDataTransformationsRepository repository;

    public DataTransformationHoarder(SensorDataTransformationsRepository repository) {
        this.repository = repository;
    }

    public void hoard(DataTransformation records) {
        this.repository.save(records);
    }
}
