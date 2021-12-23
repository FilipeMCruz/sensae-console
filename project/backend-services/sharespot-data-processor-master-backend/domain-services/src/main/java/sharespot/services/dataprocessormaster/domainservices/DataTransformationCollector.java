package sharespot.services.dataprocessormaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorDataTransformationsRepository;

import java.util.Set;

@Service
public class DataTransformationCollector {

    private final SensorDataTransformationsRepository repository;

    public DataTransformationCollector(SensorDataTransformationsRepository repository) {
        this.repository = repository;
    }

    public Set<DataTransformation> collect() {
        return repository.findAll();
    }
}
