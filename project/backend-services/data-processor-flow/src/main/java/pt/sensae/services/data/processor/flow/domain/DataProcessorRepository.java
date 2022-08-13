package pt.sensae.services.data.processor.flow.domain;

import java.util.Optional;

public interface DataProcessorRepository {

    Optional<DataTransformation> findById(SensorTypeId id);

    void update(DataTransformation info);

    void delete(SensorTypeId id);
}
