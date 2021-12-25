package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper.RecordMapper;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository.RecordsRepositoryPostgres;

import java.util.Optional;

@Repository
public class RecordsRepositoryImpl implements RecordsRepository {

    Logger logger = LoggerFactory.getLogger(RecordsRepositoryImpl.class);

    private final RecordsRepositoryPostgres repositoryPostgres;

    public RecordsRepositoryImpl(RecordsRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    public Optional<DeviceRecords> findByDeviceId(DeviceId id) {
        logger.info("Repository: " + id.value());
        return repositoryPostgres.findByDeviceId(id.value().toString())
                .map(RecordMapper::postgresToDomain);
    }
}
