package sharespot.services.dataprocessormaster.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorDataTransformationsRepository;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.mapper.DataTransformationMapper;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model.DataTransformationPostgres;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.repository.SensorDataTransformationsRepositoryPostgres;

import java.util.Optional;
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
    @Transactional
    public DataTransformation save(DataTransformation domain) {
        var id = domain.getId();
        var transformationPostgres = DataTransformationMapper.domainToPostgres(domain);

        var byDeviceType = repositoryPostgres.findByDeviceType(id.getValue());
        if(byDeviceType.isPresent()) {
            var old = byDeviceType.get();
            old.entries.clear();
            old.entries.addAll(transformationPostgres.entries);
            repositoryPostgres.save(old);
        } else {
            repositoryPostgres.save(transformationPostgres);
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
