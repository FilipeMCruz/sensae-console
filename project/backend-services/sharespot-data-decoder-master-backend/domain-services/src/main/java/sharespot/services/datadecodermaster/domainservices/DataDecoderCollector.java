package sharespot.services.datadecodermaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.datadecodermaster.domain.DataDecoder;
import sharespot.services.datadecodermaster.domain.SensorDataDecodersRepository;

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
}
