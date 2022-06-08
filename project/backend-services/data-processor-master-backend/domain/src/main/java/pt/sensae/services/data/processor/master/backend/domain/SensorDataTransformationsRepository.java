package pt.sensae.services.data.processor.master.backend.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface SensorDataTransformationsRepository {

    DataTransformation save(DataTransformation records);

    Stream<DataTransformation> findAll();

    SensorTypeId delete(SensorTypeId id);

    Optional<DataTransformation> findById(SensorTypeId id);
}
