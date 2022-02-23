package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.device.domains.DeviceDomainPermissionsDTO;
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
        var domains = cache.findByDeviceId(DeviceId.of(dto.dataId));
        var deviceDomainPermissionsDTO = new DeviceDomainPermissionsDTO();
        deviceDomainPermissionsDTO.readWrite = domains.getReadWriteDomains().stream().map(DomainId::value).collect(Collectors.toSet());
        deviceDomainPermissionsDTO.read = domains.getReadDomains().stream().map(DomainId::value).collect(Collectors.toSet());
        dto.device.domains = deviceDomainPermissionsDTO;
        return dto;
    }
}
