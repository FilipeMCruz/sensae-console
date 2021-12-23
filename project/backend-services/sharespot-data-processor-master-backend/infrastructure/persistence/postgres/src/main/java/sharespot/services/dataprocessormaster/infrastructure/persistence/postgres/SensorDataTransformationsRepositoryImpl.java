package sharespot.services.dataprocessormaster.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorDataTransformationsRepository;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.mapper.DataTransformationMapper;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.repository.SensorDataTransformationsRepositoryPostgres;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class SensorDataTransformationsRepositoryImpl implements SensorDataTransformationsRepository {

    private final SensorDataTransformationsRepositoryPostgres repositoryPostgres;

    public SensorDataTransformationsRepositoryImpl(SensorDataTransformationsRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    public DataTransformation save(DataTransformation domain) {
        var byDeviceId = repositoryPostgres.findByDeviceType(domain.getId().getValue());
        var deviceRecordsPostgres = DataTransformationMapper.domainToPostgres(domain);
        if (byDeviceId.isEmpty()) {
            repositoryPostgres.save(deviceRecordsPostgres);
        } else {
            var deviceRecords = byDeviceId.get();
            deviceRecords.entries = deviceRecordsPostgres.entries;
        }
        return domain;
    }

    @Override
    public Set<DataTransformation> findAll() {
        return StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false)
                .map(DataTransformationMapper::postgresToDomain)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public SensorTypeId delete(SensorTypeId id) {
        repositoryPostgres.deleteByDeviceType(id.getValue());
        return id;
    }
}
