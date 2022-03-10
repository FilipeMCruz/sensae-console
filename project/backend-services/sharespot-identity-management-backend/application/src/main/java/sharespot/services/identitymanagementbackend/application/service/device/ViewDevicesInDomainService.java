package sharespot.services.identitymanagementbackend.application.service.device;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.ViewDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.device.ViewDevicesInDomain;

import java.util.stream.Stream;

@Service
public class ViewDevicesInDomainService {

    private final ViewDevicesInDomain service;

    private final TenantMapper tenantMapper;

    private final DomainMapper domainMapper;

    private final DeviceMapper deviceMapper;

    public ViewDevicesInDomainService(ViewDevicesInDomain service, TenantMapper tenantMapper, DomainMapper domainMapper, DeviceMapper deviceMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.domainMapper = domainMapper;
        this.deviceMapper = deviceMapper;
    }

    public Stream<DeviceDTO> fetch(ViewDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = domainMapper.dtoToCommand(dto);
        return service.fetch(createDomainCommand, identityCommand)
                .map(deviceMapper::resultToDto);
    }
}
