package sharespot.services.dataprocessor.domain;

import java.util.Optional;

public interface SensorDataTransformationsRepository {

    Optional<DataTransformation> findByDeviceType(SensorTypeId id);
}
