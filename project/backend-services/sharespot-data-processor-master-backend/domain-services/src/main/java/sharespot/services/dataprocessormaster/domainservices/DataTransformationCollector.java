package sharespot.services.dataprocessormaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorDataTransformationsRepository;

import java.util.Set;
import java.util.stream.Stream;

@Service
public class DataTransformationCollector {

    private final SensorDataTransformationsRepository repository;

    public DataTransformationCollector(SensorDataTransformationsRepository repository) {
        this.repository = repository;
    }

    public Stream<DataTransformation> collect() {
        return repository.findAll();
    }
}
