package pt.sensae.services.data.decoder.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecodersRepository;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;

@Service
public class DataDecoderEraser {

    private final DataDecodersRepository repository;

    public DataDecoderEraser(DataDecodersRepository repository) {
        this.repository = repository;
    }

    public SensorTypeId erase(SensorTypeId deviceId) {
        return repository.delete(deviceId);
    }
}
