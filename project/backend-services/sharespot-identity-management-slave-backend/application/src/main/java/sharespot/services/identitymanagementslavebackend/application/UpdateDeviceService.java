package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementslavebackend.domainservices.DeviceDomainCache;

@Service
public class UpdateDeviceService {

    private final DeviceDomainCache cache;

    private final RecordEventMapper mapper;

    public UpdateDeviceService(DeviceDomainCache cache, RecordEventMapper mapper) {
        this.cache = cache;
        this.mapper = mapper;
    }

    public void update(DeviceIdDTO dto) {
        var deviceId = mapper.dtoToDomain(dto);
        cache.update(deviceId);
    }
}
