package sharespot.services.datadecodermaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorDataDecodersRepository;
import sharespot.services.data.decoder.master.domain.SensorTypeId;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DataDecoderCollector {

    private final SensorDataDecodersRepository repository;

    public DataDecoderCollector(SensorDataDecodersRepository repository) {
        this.repository = repository;
    }

    public Stream<DataDecoder> collect() {
        return repository.findAll();
    }

    public Optional<DataDecoder> collect(SensorTypeId type) {
        return repository.findById(type);
    }
}
