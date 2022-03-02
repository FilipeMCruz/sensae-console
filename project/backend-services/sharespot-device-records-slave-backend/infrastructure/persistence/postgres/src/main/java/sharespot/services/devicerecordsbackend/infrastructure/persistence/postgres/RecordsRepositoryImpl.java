package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper.RecordMapper;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository.RecordsRepositoryPostgres;

import java.util.Optional;

@Repository
public class RecordsRepositoryImpl implements RecordsRepository {

    private final RecordsRepositoryPostgres repositoryPostgres;

    public RecordsRepositoryImpl(RecordsRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    public Optional<DeviceRecords> findByDeviceId(DeviceId id) {
        return repositoryPostgres.findByDeviceId(id.value().toString())
                .map(RecordMapper::postgresToDomain);
    }

    @Override
    public DeviceRecords add(DeviceRecords domain) {
        var deviceRecordsPostgres = RecordMapper.domainToPostgres(domain);
        repositoryPostgres.save(deviceRecordsPostgres);
        return RecordMapper.postgresToDomain(deviceRecordsPostgres);
    }
}
