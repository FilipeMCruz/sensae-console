package pt.sensae.services.identity.management.backend.application.internal.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.domainservices.service.device.DeviceInformationCache;

import java.util.stream.Stream;

@Service
public class DeviceInformationNotificationHandler {

    private final DeviceInformationCache cache;

    private final DeviceInformationMapper mapper;

    public DeviceInformationNotificationHandler(DeviceInformationCache cache, DeviceInformationMapper mapper) {
        this.cache = cache;
        this.mapper = mapper;
    }

    public void info(DeviceNotificationDTO dto) {
        this.cache.notify(mapper.dtoToDomain(dto));
    }

    public void sync(Stream<DeviceNotificationDTO> dtoStream) {
        this.cache.refresh(dtoStream.map(mapper::dtoToDomain));
    }
}
