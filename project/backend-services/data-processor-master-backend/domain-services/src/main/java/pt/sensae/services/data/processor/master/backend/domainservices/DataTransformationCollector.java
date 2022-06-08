package pt.sensae.services.data.processor.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.domain.DataTransformation;
import pt.sensae.services.data.processor.master.backend.domain.SensorDataTransformationsRepository;
import pt.sensae.services.data.processor.master.backend.domain.SensorTypeId;

import java.util.Optional;
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

    public Optional<DataTransformation> collect(SensorTypeId type) {
        return repository.findById(type);
    }
}
