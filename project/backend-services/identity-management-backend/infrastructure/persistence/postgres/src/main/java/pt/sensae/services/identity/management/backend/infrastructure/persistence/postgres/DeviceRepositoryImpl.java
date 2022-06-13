package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domain.identity.device.Device;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceId;
import pt.sensae.services.identity.management.backend.domain.identity.device.DeviceRepository;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.repository.DeviceRepositoryPostgres;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.mapper.DeviceMapper;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
    @Transactional
    public Device relocateDevice(Device device) {
        var domains = DeviceMapper.domainToPostgres(device).devicePermissions;
        repository.findByOid(device.oid().value().toString()).ifPresent(devicePostgres -> {
            devicePostgres.devicePermissions.clear();
            devicePostgres.devicePermissions.addAll(domains);
            devicePostgres.devicePermissions.forEach(d -> d.device = devicePostgres);
            repository.save(devicePostgres);
        });
        return device;
    }

    @Override
    public Stream<Device> getDevicesInDomains(Stream<DomainId> domains) {
        return repository.findByDomainIds(domains.map(d -> d.value().toString()).collect(Collectors.toList()))
                .stream()
                .map(DeviceMapper::postgresToDomain);
    }

    @Override
    public Device add(Device device) {
        var devicePostgres = DeviceMapper.domainToPostgres(device);
        var saved = repository.save(devicePostgres);
        return DeviceMapper.postgresToDomain(saved);
    }

    @Override
    @Transactional
    public Stream<Device> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(DeviceMapper::postgresToDomain).toList().stream();
    }
}
