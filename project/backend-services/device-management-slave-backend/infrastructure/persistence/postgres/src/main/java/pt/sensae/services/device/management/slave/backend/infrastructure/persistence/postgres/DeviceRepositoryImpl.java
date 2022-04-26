package pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import pt.sensae.services.device.management.slave.backend.domain.model.DeviceRepository;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceRecords;
import pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres.mapper.RecordMapper;
import pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres.repository.DeviceRepositoryPostgres;

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
