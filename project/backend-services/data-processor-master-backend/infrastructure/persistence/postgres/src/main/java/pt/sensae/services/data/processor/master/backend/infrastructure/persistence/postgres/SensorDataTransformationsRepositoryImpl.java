package pt.sensae.services.data.processor.master.backend.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.sensae.services.data.processor.master.backend.domain.DataTransformation;
import pt.sensae.services.data.processor.master.backend.domain.SensorDataTransformationsRepository;
import pt.sensae.services.data.processor.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.processor.master.backend.infrastructure.persistence.postgres.mapper.DataTransformationMapper;
import pt.sensae.services.data.processor.master.backend.infrastructure.persistence.postgres.repository.SensorDataTransformationsRepositoryPostgres;

import java.util.Optional;
import java.util.stream.Stream;
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
        var transformationPostgres = DataTransformationMapper.domainToPostgres(domain);

        var byDeviceType = repositoryPostgres.findByDeviceType(domain.getId().getValue());
        if (byDeviceType.isPresent()) {
            var old = byDeviceType.get();
            old.entries.clear();
            old.entries.addAll(transformationPostgres.entries);
            old.entries.forEach(e -> e.transformation = old);
            repositoryPostgres.save(old);
        } else {
            repositoryPostgres.save(transformationPostgres);
        }

        return domain;
    }

    @Override
    public Stream<DataTransformation> findAll() {
        return StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false)
                .map(DataTransformationMapper::postgresToDomain)
                .distinct();
    }

    @Override
    @Transactional
    public SensorTypeId delete(SensorTypeId id) {
        repositoryPostgres.deleteByDeviceType(id.getValue());
        return id;
    }

    @Override
    @Transactional
    public Optional<DataTransformation> findById(SensorTypeId id) {
        return repositoryPostgres.findByDeviceType(id.getValue())
                .map(DataTransformationMapper::postgresToDomain);
    }
}
