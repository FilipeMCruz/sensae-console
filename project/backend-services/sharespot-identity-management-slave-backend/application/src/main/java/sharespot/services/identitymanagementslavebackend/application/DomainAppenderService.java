package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;
import sharespot.services.identitymanagementslavebackend.domainservices.DeviceDomainCache;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainAppenderService {

    private final DeviceDomainCache cache;

    public DomainAppenderService(DeviceDomainCache cache) {
        this.cache = cache;
    }

    public Optional<SensorDataDTO> tryToAppend(SensorDataDTO dto) {
        return cache.findById(DeviceId.of(dto.device.id)).map(device -> {
            dto.device.domains = device.getOwnerDomains().stream().map(DomainId::value).collect(Collectors.toSet());
            return dto;
        });
    }
}
