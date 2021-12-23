package sharespot.services.dataprocessor.application;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessor.domain.SensorTypeId;
import sharespot.services.dataprocessor.domainservices.DataTransformationCache;

@Service
public class SensorDataTransformationsUpdateService {

    private final DataTransformationCache cache;

    public SensorDataTransformationsUpdateService(DataTransformationCache cache) {
        this.cache = cache;
    }

    public void update(SensorTypeId id) {
        cache.update(id);
    }
}
