package pt.sensae.services.device.management.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.records.RecordsRepository;

@Service
public class RecordHoarder {

    private final RecordsRepository repository;

    public RecordHoarder(RecordsRepository repository) {
        this.repository = repository;
    }

    public void hoard(DeviceInformation records) {
        this.repository.save(records);
    }
}
