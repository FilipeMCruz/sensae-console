package pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import pt.sensae.services.device.management.slave.backend.domain.model.DeviceRepository;
import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceInformation;
import pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres.mapper.DeviceMapper;
import pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres.repository.DeviceRepositoryPostgres;

import java.util.Optional;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceRepositoryPostgres repositoryPostgres;

    public DeviceRepositoryImpl(DeviceRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    public Optional<DeviceInformation> findByDeviceId(DeviceId id) {
        return repositoryPostgres.findByDeviceId(id.value().toString()).map(DeviceMapper::postgresToDomain);
    }

    @Override
    public DeviceInformation add(DeviceInformation domain) {
        var deviceRecordsPostgres = DeviceMapper.domainToPostgres(domain);
        repositoryPostgres.save(deviceRecordsPostgres);
        return DeviceMapper.postgresToDomain(deviceRecordsPostgres);
    }

    @Override
    public void updateDownlink(Device domain) {
        repositoryPostgres.findByDeviceId(domain.id().value().toString()).ifPresent(dev -> {
            dev.downlink = domain.downlink().value();
            repositoryPostgres.save(dev);
        });
    }
}
