package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sharespot.services.devicerecordsbackend.domain.model.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper.RecordMapper;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository.RecordsRepositoryPostgres;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class RecordsRepositoryImpl implements RecordsRepository {

    private final RecordsRepositoryPostgres repositoryPostgres;

    public RecordsRepositoryImpl(RecordsRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    @Transactional
    public DeviceRecords save(DeviceRecords domain) {
        var id = domain.device().id();
        var deviceRecordsPostgres = RecordMapper.domainToPostgres(domain);

        var byDeviceId = repositoryPostgres.findByDeviceId(id.value().toString());
        if (byDeviceId.isPresent()) {
            var old = byDeviceId.get();
            old.entries.clear();
            old.entries.addAll(deviceRecordsPostgres.entries);
            old.entries.forEach(e -> e.records = old);
            old.name = deviceRecordsPostgres.name;
            repositoryPostgres.save(old);
        } else {
            repositoryPostgres.save(deviceRecordsPostgres);
        }
        return domain;
    }

    @Override
    public Stream<DeviceRecords> findAll() {
        return StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false)
                .map(RecordMapper::postgresToDomain);
    }

    @Override
    @Transactional
    public DeviceId delete(DeviceId id) {
        repositoryPostgres.deleteByDeviceId(id.value().toString());
        return id;
    }
}
