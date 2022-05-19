package pt.sensae.services.device.management.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceInformationRepository;

@Service
public class RecordHoarder {

    private final DeviceInformationRepository repository;

    public RecordHoarder(DeviceInformationRepository repository) {
        this.repository = repository;
    }

    public DeviceInformation hoard(DeviceInformation information) {
        return this.repository.save(information);
    }

    public boolean exists(DeviceId deviceId) {
        return this.repository.findById(deviceId).isPresent();
    }
}
