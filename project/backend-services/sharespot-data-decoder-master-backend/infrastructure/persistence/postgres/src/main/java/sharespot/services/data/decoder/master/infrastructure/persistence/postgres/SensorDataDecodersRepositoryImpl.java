package sharespot.services.data.decoder.master.infrastructure.persistence.postgres;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorDataDecodersRepository;
import sharespot.services.data.decoder.master.domain.SensorTypeId;
import sharespot.services.data.decoder.master.infrastructure.persistence.postgres.mapper.DataDecoderMapper;
import sharespot.services.data.decoder.master.infrastructure.persistence.postgres.repository.SensorDataTransformationsRepositoryPostgres;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
public class SensorDataDecodersRepositoryImpl implements SensorDataDecodersRepository {

    private final SensorDataTransformationsRepositoryPostgres repositoryPostgres;

    public SensorDataDecodersRepositoryImpl(SensorDataTransformationsRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    @Transactional
    public DataDecoder save(DataDecoder domain) {
        var transformationPostgres = DataDecoderMapper.domainToPostgres(domain);

        var byDeviceType = repositoryPostgres.findByDeviceType(domain.id().getValue());
        if (byDeviceType.isPresent()) {
            var old = byDeviceType.get();
            old.script = transformationPostgres.script;
            repositoryPostgres.save(old);
        } else {
            repositoryPostgres.save(transformationPostgres);
        }

        return domain;
    }

    @Override
    public Stream<DataDecoder> findAll() {
        return StreamSupport.stream(repositoryPostgres.findAll().spliterator(), false)
                .map(DataDecoderMapper::postgresToDomain)
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
    public Optional<DataDecoder> findById(SensorTypeId id) {
        return repositoryPostgres.findByDeviceType(id.getValue())
                .map(DataDecoderMapper::postgresToDomain);
    }
}