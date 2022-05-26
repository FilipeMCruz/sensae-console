package sharespot.services.datadecodermaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorDataDecodersRepository;

@Service
public class DataDecoderHoarder {

    private final SensorDataDecodersRepository repository;

    public DataDecoderHoarder(SensorDataDecodersRepository repository) {
        this.repository = repository;
    }

    public DataDecoder hoard(DataDecoder records) {
        return this.repository.save(records);
    }
}
