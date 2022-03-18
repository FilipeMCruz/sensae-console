package sharespot.services.datadecoder.application;

import org.springframework.stereotype.Service;
import sharespot.services.datadecoder.domain.SensorTypeId;
import sharespot.services.datadecoder.domainservices.DataDecoderCache;

@Service
public class DataDecodersUpdateService {

    private final DataDecoderCache executor;

    public DataDecodersUpdateService(DataDecoderCache executor) {
        this.executor = executor;
    }

    public void update(SensorTypeId id) {
        executor.update(id);
    }
}
