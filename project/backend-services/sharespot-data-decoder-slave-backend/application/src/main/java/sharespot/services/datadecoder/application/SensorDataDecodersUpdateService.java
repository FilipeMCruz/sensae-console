package sharespot.services.datadecoder.application;

import org.springframework.stereotype.Service;
import sharespot.services.datadecoder.domain.SensorTypeId;
import sharespot.services.datadecoder.domainservices.DataDecoderCache;

@Service
public class SensorDataDecodersUpdateService {

    private final DataDecoderCache cache;

    public SensorDataDecodersUpdateService(DataDecoderCache cache) {
        this.cache = cache;
    }

    public void update(SensorTypeId id) {
        cache.update(id);
    }
}
