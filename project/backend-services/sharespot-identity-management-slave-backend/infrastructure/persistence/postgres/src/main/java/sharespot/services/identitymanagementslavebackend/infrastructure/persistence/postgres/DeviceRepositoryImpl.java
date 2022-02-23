package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.Device;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceRepository;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.mapper.DeviceMapper;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.repository.DeviceRepositoryPostgres;

import java.util.Optional;

@Service
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceRepositoryPostgres repository;

    public DeviceRepositoryImpl(DeviceRepositoryPostgres repository) {
        this.repository = repository;
    }
    
    @Override
    public Device add(Device device) {
        var devicePostgres = DeviceMapper.domainToPostgres(device);
        var saved = repository.save(devicePostgres);
        return DeviceMapper.postgresToDomain(saved);
    }

    @Override
    public Optional<Device> findDeviceById(DeviceId id) {
        return repository.findByOid(id.value().toString())
                .map(DeviceMapper::postgresToDomain);
    }
}
