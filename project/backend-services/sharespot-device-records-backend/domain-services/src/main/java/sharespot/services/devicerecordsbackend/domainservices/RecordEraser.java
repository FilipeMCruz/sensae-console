package sharespot.services.devicerecordsbackend.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;

@Service
public class RecordEraser {

    private final RecordsCache cache;

    public RecordEraser(RecordsCache cache) {
        this.cache = cache;
    }

    public DeviceId erase(DeviceId deviceId) {
        return cache.removeRecord(deviceId);
    }
}
