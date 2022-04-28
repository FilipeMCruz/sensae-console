package pt.sensae.services.device.commander.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.domainservices.DeviceRecordCache;

@Service
public class RecordRegisterService {

    private final DeviceRecordCache cache;

    private final RecordEventMapper mapper;

    public RecordRegisterService(DeviceRecordCache cache, RecordEventMapper mapper) {
        this.cache = cache;
        this.mapper = mapper;
    }

    public void update(DeviceIdDTO dto) {
        var deviceId = mapper.dtoToDomain(dto);
        cache.update(deviceId);
    }
}
