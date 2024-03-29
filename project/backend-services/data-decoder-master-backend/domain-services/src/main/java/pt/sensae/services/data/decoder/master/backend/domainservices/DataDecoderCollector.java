package pt.sensae.services.data.decoder.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecodersRepository;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DataDecoderCollector {

    private final DataDecodersRepository repository;

    public DataDecoderCollector(DataDecodersRepository repository) {
        this.repository = repository;
    }

    public Stream<DataDecoder> collect() {
        return repository.findAll();
    }

    public Optional<DataDecoder> collect(SensorTypeId type) {
        return repository.findById(type);
    }
}
