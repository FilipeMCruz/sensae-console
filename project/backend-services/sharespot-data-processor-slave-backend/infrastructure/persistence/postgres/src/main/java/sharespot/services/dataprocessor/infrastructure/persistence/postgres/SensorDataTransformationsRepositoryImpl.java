package sharespot.services.dataprocessor.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import sharespot.services.dataprocessor.domain.DataTransformation;
import sharespot.services.dataprocessor.domain.SensorDataTransformationsRepository;
import sharespot.services.dataprocessor.domain.SensorTypeId;
import sharespot.services.dataprocessor.infrastructure.persistence.postgres.mapper.DataTransformationMapper;
import sharespot.services.dataprocessor.infrastructure.persistence.postgres.repository.SensorDataTransformationsRepositoryPostgres;

import java.util.Optional;

@Repository
public class SensorDataTransformationsRepositoryImpl implements SensorDataTransformationsRepository {

    private final SensorDataTransformationsRepositoryPostgres repository;

    public SensorDataTransformationsRepositoryImpl(SensorDataTransformationsRepositoryPostgres repository) {
        this.repository = repository;
    }

    @Override
    public Optional<DataTransformation> findByDeviceId(SensorTypeId id) {
        return repository.findByDeviceType(id.getValue())
                .map(DataTransformationMapper::postgresToDomain);
    }
}
