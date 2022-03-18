package sharespot.services.datadecoder.infrastructure.persistence.filesystem;

import org.springframework.stereotype.Service;
import sharespot.services.datadecoder.domain.DataDecoder;
import sharespot.services.datadecoder.domain.SensorDataDecodersRepository;
import sharespot.services.datadecoder.domain.SensorTypeId;
import sharespot.services.datadecoder.infrastructure.persistence.filesystem.mapper.DataDecoderMapper;
import sharespot.services.datadecoder.infrastructure.persistence.filesystem.repository.SensorDataDecoderRepositoryPostgres;

import java.util.Optional;

@Service
public class SensorDataDecodersRepositoryImpl implements SensorDataDecodersRepository {

    private final SensorDataDecoderRepositoryPostgres repositoryPostgres;

    public SensorDataDecodersRepositoryImpl(SensorDataDecoderRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    @Override
    public Optional<DataDecoder> findByDeviceType(SensorTypeId id) {
        return repositoryPostgres.findByDeviceType(id.getValue())
                .map(DataDecoderMapper::postgresToDomain);
    }
}
