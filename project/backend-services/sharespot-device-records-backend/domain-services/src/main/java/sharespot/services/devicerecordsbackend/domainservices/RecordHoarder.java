package sharespot.services.devicerecordsbackend.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;

@Service
public class RecordHoarder {

    private final RecordsCache cache;

    public RecordHoarder(RecordsCache cache) {
        this.cache = cache;
    }

    public void hoard(DeviceRecords records) {
        cache.indexRecord(records);
    }
}
