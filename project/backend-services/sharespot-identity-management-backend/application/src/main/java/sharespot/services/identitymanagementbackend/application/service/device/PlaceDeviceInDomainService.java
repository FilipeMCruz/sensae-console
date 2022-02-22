package sharespot.services.identitymanagementbackend.application.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;
import sharespot.services.identitymanagementbackend.application.model.device.ExpelDeviceFromDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.device.PlaceDeviceInDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.device.MoveDevice;

@Service
public class PlaceDeviceInDomainService {

    private final MoveDevice service;

    private final TenantMapper tenantMapper;

    private final DeviceMapper deviceMapper;

    public PlaceDeviceInDomainService(MoveDevice service, TenantMapper tenantMapper, DeviceMapper deviceMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.deviceMapper = deviceMapper;
    }

    public DeviceDTO place(PlaceDeviceInDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = deviceMapper.dtoToCommand(dto);
        return deviceMapper.resultToDto(service.execute(createDomainCommand, identityCommand));
    }

    public DeviceDTO expel(ExpelDeviceFromDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = deviceMapper.dtoToCommand(dto);
        return deviceMapper.resultToDto(service.execute(createDomainCommand, identityCommand));
    }
}
