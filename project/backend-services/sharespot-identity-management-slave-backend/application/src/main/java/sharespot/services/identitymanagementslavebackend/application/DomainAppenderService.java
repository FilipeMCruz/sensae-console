package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.model.device.domains.DeviceDomainPermissionsDTO;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;
import sharespot.services.identitymanagementslavebackend.domainservices.DeviceDomainCache;

import java.util.stream.Collectors;

@Service
public class DomainAppenderService {

    private final DeviceDomainCache cache;

    public DomainAppenderService(DeviceDomainCache cache) {
        this.cache = cache;
    }

    public ProcessedSensorDataDTO append(ProcessedSensorDataDTO dto) {
        var domains = cache.findByDeviceId(DeviceId.of(dto.device.id));
        var deviceDomainPermissionsDTO = new DeviceDomainPermissionsDTO();
        deviceDomainPermissionsDTO.ownership = domains.getOwnerDomains().stream().map(DomainId::value).collect(Collectors.toSet());
        dto.device.domains = deviceDomainPermissionsDTO;
        return dto;
    }
}
