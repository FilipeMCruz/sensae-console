package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.domain.identity.device.Device;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceRepository;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.mapper.DeviceMapper;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository.DeviceRepositoryPostgres;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceRepositoryPostgres repository;

    public DeviceRepositoryImpl(DeviceRepositoryPostgres repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Device> findDeviceById(DeviceId id) {
        return repository.findByOid(id.value().toString())
                .map(DeviceMapper::postgresToDomain);
    }

    @Override
    public Device relocateDevice(Device tenant) {
        repository.deleteByOid(tenant.getOid().value().toString());
        var tenantPostgres = DeviceMapper.domainToPostgres(tenant);
        return DeviceMapper.postgresToDomain(repository.save(tenantPostgres));
    }

    @Override
    public List<Device> getDevicesInDomain(DomainId domain) {
        return repository.findByDomainId(domain.value().toString())
                .stream()
                .map(DeviceMapper::postgresToDomain)
                .toList();
    }
}
