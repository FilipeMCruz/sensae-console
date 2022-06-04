package pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformationRepository;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.mapper.DeviceInformationMapper;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.repository.DeviceInformationRepositoryPostgres;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class DeviceInformationRepositoryImpl implements DeviceInformationRepository {

    private final DeviceInformationRepositoryPostgres repositoryPostgres;

    public DeviceInformationRepositoryImpl(DeviceInformationRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    @Transactional
    public DeviceInformation save(DeviceInformation domain) {
        var id = domain.device().id();
        var deviceRecordsPostgres = DeviceInformationMapper.domainToPostgres(domain);

        var byDeviceId = repositoryPostgres.findByDeviceId(id.value().toString());
        if (byDeviceId.isPresent()) {
            var old = byDeviceId.get();

            old.name = deviceRecordsPostgres.name;
            old.downlink = deviceRecordsPostgres.downlink;

            old.entries.clear();
            old.entries.addAll(deviceRecordsPostgres.entries);
            old.entries.forEach(e -> e.records = old);

            deviceRecordsPostgres.subSensors.forEach(sub -> {
                if (!old.subSensors.contains(sub)) {
                    old.subSensors.add(sub);
                    sub.controller = old;
                }
            });

            old.subSensors.forEach(sub -> {
                if (!deviceRecordsPostgres.subSensors.contains(sub)) {
                    old.subSensors.remove(sub);
                    sub.controller = null;
                }
            });

            old.commands.clear();
            old.commands.addAll(deviceRecordsPostgres.commands);
            old.commands.forEach(s -> s.device = old);

            repositoryPostgres.save(old);
        } else {
            repositoryPostgres.save(deviceRecordsPostgres);
        }
        return domain;
    }

    @Override
    public Stream<DeviceInformation> findAll() {
        return StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false)
                .map(DeviceInformationMapper::postgresToDomain);
    }

    @Override
    public Optional<DeviceInformation> findById(DeviceId id) {
        return repositoryPostgres.findByDeviceId(id.value().toString()).map(DeviceInformationMapper::postgresToDomain);
    }

    @Override
    @Transactional
    public DeviceId delete(DeviceId id) {
        repositoryPostgres.deleteByDeviceId(id.value().toString());
        return id;
    }
}
