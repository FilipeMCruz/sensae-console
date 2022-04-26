package pt.sensae.services.device.management.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.records.RecordsRepository;

@Service
public class RecordEraser {

    private final RecordsRepository repository;

    public RecordEraser(RecordsRepository repository) {
        this.repository = repository;
    }

    public DeviceId erase(DeviceId deviceId) {
        return repository.delete(deviceId);
    }
}
