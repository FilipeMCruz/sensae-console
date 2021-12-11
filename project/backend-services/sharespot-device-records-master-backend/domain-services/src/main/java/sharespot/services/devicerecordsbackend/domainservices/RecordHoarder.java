package sharespot.services.devicerecordsbackend.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;

@Service
public class RecordHoarder {

    private final RecordsRepository repository;

    public RecordHoarder(RecordsRepository repository) {
        this.repository = repository;
    }

    public void hoard(DeviceRecords records) {
        this.repository.save(records);
    }
}
