package sharespot.services.datadecoder.application;

import org.springframework.stereotype.Service;
import sharespot.services.datadecoder.domain.SensorDataDecodersRepository;
import sharespot.services.datadecoder.domain.SensorTypeId;

@Service
public class SensorDataDecodersUpdateService {

    private final SensorDataDecodersRepository repository;

    private final DataDecoderExecutor executor;

    public SensorDataDecodersUpdateService(SensorDataDecodersRepository repository, DataDecoderExecutor executor) {
        this.repository = repository;
        this.executor = executor;
    }

    public void update(SensorTypeId id) {
        var byDeviceType = repository.findByDeviceType(id);
        if (byDeviceType.isEmpty()) {
            return;
        }
        executor.addScript(id, byDeviceType.get().getScript());
    }
}
