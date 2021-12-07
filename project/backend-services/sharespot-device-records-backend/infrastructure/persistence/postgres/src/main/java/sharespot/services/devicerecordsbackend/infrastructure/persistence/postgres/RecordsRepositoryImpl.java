package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper.RecordMapper;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository.RecordsRepositoryPostgres;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class RecordsRepositoryImpl implements RecordsRepository {

    private final RecordsRepositoryPostgres repositoryPostgres;

    public RecordsRepositoryImpl(RecordsRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    public DeviceRecords save(DeviceRecords records) {
        var byDeviceId = repositoryPostgres.findByDeviceId(records.device().id().value().toString());
        var deviceRecordsPostgres = RecordMapper.domainToPostgres(records);
        if (byDeviceId.isEmpty()) {
            repositoryPostgres.save(deviceRecordsPostgres);
        } else {
            var deviceRecords = byDeviceId.get();
            deviceRecords.entries = deviceRecordsPostgres.entries;
        }
        return records;
    }

    @Override
    public Optional<DeviceRecords> findByDeviceId(DeviceId id) {
        return repositoryPostgres.findByDeviceId(id.value().toString())
                .map(RecordMapper::postgresToDomain);
    }

    @Override
    public Set<DeviceRecords> findAll() {
        return StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false)
                .map(RecordMapper::postgresToDomain)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public DeviceId delete(DeviceId id) {
        repositoryPostgres.deleteByDeviceId(id.value().toString());
        return id;
    }
}
