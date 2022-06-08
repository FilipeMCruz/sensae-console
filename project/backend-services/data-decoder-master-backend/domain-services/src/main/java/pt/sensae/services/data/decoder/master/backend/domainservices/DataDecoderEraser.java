package pt.sensae.services.data.decoder.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.domain.SensorDataDecodersRepository;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;

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
