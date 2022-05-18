package sharespot.services.dataprocessormaster.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface SensorDataTransformationsRepository {

    DataTransformation save(DataTransformation records);

    Stream<DataTransformation> findAll();

    SensorTypeId delete(SensorTypeId id);

    Optional<DataTransformation> findById(SensorTypeId id);
}
