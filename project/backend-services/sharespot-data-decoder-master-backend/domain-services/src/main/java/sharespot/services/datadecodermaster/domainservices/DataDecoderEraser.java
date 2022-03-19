package sharespot.services.datadecodermaster.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.datadecodermaster.domain.SensorDataDecodersRepository;
import sharespot.services.datadecodermaster.domain.SensorTypeId;

@Service
public class DataDecoderEraser {

    private final SensorDataDecodersRepository repository;

    public DataDecoderEraser(SensorDataDecodersRepository repository) {
        this.repository = repository;
    }

    public SensorTypeId erase(SensorTypeId deviceId) {
        return repository.delete(deviceId);
    }
}
