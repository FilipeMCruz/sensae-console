package sharespot.services.devicerecordsbackend.domainservices;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.RecordsRepository;

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
