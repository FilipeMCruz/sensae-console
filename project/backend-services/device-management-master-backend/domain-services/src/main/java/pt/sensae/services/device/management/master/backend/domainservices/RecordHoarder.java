package pt.sensae.services.device.management.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceRecords;
import pt.sensae.services.device.management.master.backend.domain.model.records.RecordsRepository;

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
