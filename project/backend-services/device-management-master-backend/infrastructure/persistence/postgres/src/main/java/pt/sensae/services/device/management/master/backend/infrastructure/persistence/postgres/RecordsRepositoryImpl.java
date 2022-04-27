package pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.records.RecordsRepository;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.mapper.RecordMapper;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.repository.RecordsRepositoryPostgres;

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
    public DeviceInformation save(DeviceInformation domain) {
        var id = domain.device().id();
        var deviceRecordsPostgres = RecordMapper.domainToPostgres(domain);

        var byDeviceId = repositoryPostgres.findByDeviceId(id.value().toString());
        if (byDeviceId.isPresent()) {
            var old = byDeviceId.get();

            old.name = deviceRecordsPostgres.name;
            old.downlink = deviceRecordsPostgres.downlink;

            old.entries.clear();
            old.entries.addAll(deviceRecordsPostgres.entries);
            old.entries.forEach(e -> e.records = old);

            old.subSensors.clear();
            old.subSensors.addAll(deviceRecordsPostgres.subSensors);
            old.subSensors.forEach(s -> s.controller = old);

            repositoryPostgres.save(old);
        } else {
            repositoryPostgres.save(deviceRecordsPostgres);
        }
        return domain;
    }

    @Override
    public Stream<DeviceInformation> findAll() {
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
