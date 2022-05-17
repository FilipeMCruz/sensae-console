package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.model.device.domains.DeviceDomainPermissionsDTO;
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

    public Optional<ProcessedSensorDataDTO> tryToAppend(ProcessedSensorDataDTO dto) {
        return cache.findById(DeviceId.of(dto.device.id)).map(device -> {
            var deviceDomainPermissionsDTO = new DeviceDomainPermissionsDTO();
            deviceDomainPermissionsDTO.ownership = device.getOwnerDomains().stream().map(DomainId::value).collect(Collectors.toSet());
            dto.device.domains = deviceDomainPermissionsDTO;
            return dto;
        });
    }
}
