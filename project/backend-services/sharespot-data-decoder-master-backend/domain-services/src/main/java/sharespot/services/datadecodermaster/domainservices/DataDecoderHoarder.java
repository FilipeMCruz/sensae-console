package sharespot.services.datadecodermaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.datadecodermaster.domain.DataDecoder;
import sharespot.services.datadecodermaster.domain.SensorDataDecodersRepository;

@Service
public class DataDecoderHoarder {

    private final SensorDataDecodersRepository repository;

    public DataDecoderHoarder(SensorDataDecodersRepository repository) {
        this.repository = repository;
    }

    public void hoard(DataDecoder records) {
        this.repository.save(records);
    }
}
