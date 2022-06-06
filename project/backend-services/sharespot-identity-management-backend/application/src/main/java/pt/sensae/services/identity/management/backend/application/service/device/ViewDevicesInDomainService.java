package pt.sensae.services.identity.management.backend.application.service.device;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.device.DeviceMapper;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.device.DeviceIdDTO;
import pt.sensae.services.identity.management.backend.application.model.domain.ViewDomainDTO;
import pt.sensae.services.identity.management.backend.domainservices.service.device.ViewDevicesInDomain;

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

    public Stream<DeviceIdDTO> fetch(ViewDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = domainMapper.dtoToCommand(dto);
        return service.fetch(createDomainCommand, identityCommand)
                .map(deviceMapper::resultToDto);
    }
}
