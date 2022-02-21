package sharespot.services.identitymanagementbackend.application.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.device.NewDomainForDeviceDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.device.PlaceDeviceInDomain;

@Service
public class PlaceDeviceInDomainService {

    private final PlaceDeviceInDomain service;

    private final TenantMapper tenantMapper;

    private final DeviceMapper deviceMapper;

    public PlaceDeviceInDomainService(PlaceDeviceInDomain service, TenantMapper tenantMapper, DeviceMapper deviceMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.deviceMapper = deviceMapper;
    }

    public void place(NewDomainForDeviceDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = deviceMapper.dtoToCommand(dto);
        service.execute(createDomainCommand, identityCommand);
    }
}
