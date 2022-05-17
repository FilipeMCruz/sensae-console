package pt.sensae.services.device.management.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceInformationRepository;

@Service
public class RecordHoarder {

    private final DeviceInformationRepository repository;

    public RecordHoarder(DeviceInformationRepository repository) {
        this.repository = repository;
    }

    public DeviceInformation hoard(DeviceInformation records) {
        return this.repository.save(records);
    }
}
