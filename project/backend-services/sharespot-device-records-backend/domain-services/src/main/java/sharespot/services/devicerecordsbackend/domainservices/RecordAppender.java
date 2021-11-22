package sharespot.services.devicerecordsbackend.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorData;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorDataWithRecord;

@Service
public class RecordAppender {

    private final RecordsCache cache;

    public RecordAppender(RecordsCache cache) {
        this.cache = cache;
    }

    public ProcessedSensorDataWithRecord appendRecord(ProcessedSensorData data) {
        var records = cache.seekRecordsFor(new DeviceId(data.getDeviceId()));
        return new ProcessedSensorDataWithRecord(data, records);
    }
}
