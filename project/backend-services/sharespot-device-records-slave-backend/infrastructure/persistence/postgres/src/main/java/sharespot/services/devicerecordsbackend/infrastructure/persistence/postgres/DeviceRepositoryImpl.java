package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.DeviceRepository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper.RecordMapper;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository.DeviceRepositoryPostgres;

import java.util.Optional;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceRepositoryPostgres repositoryPostgres;

    public DeviceRepositoryImpl(DeviceRepositoryPostgres repositoryPostgres) {
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
