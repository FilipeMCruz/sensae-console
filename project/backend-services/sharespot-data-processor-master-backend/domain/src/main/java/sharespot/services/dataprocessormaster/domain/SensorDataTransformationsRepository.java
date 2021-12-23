package sharespot.services.dataprocessormaster.domain;

import java.util.Set;

public interface SensorDataTransformationsRepository {

    DataTransformation save(DataTransformation records);

    Set<DataTransformation> findAll();

    SensorTypeId delete(SensorTypeId id);
}
