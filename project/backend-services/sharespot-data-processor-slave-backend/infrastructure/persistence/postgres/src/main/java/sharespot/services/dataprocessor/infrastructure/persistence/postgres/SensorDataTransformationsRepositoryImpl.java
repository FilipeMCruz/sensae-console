package sharespot.services.dataprocessor.infrastructure.persistence.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharespot.services.dataprocessor.domain.DataTransformation;
import sharespot.services.dataprocessor.domain.SensorDataTransformationsRepository;
import sharespot.services.dataprocessor.domain.SensorTypeId;
import sharespot.services.dataprocessor.infrastructure.persistence.postgres.mapper.DataTransformationMapper;
import sharespot.services.dataprocessor.infrastructure.persistence.postgres.repository.SensorDataTransformationsRepositoryPostgres;

import java.util.Optional;

@Repository
public class SensorDataTransformationsRepositoryImpl implements SensorDataTransformationsRepository {

    Logger logger = LoggerFactory.getLogger(SensorDataTransformationsRepositoryImpl.class);

    private final SensorDataTransformationsRepositoryPostgres repository;

    public SensorDataTransformationsRepositoryImpl(SensorDataTransformationsRepositoryPostgres repository) {
        this.repository = repository;
    }

    @Override
    public Optional<DataTransformation> findByDeviceId(SensorTypeId id) {
        logger.info("Data fetched for " + id.getValue());
        return repository.findByDeviceType(id.getValue())
                .map(DataTransformationMapper::postgresToDomain);
    }
}
