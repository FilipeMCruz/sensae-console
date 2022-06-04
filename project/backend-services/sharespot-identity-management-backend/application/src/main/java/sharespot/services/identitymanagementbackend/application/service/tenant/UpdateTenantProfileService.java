package sharespot.services.identitymanagementbackend.application.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.tenant.*;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.MoveTenant;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.UpdateTenantProfile;

@Service
public class UpdateTenantProfileService {

    private final UpdateTenantProfile service;

    private final TenantMapper tenantMapper;

    public UpdateTenantProfileService(UpdateTenantProfile service, TenantMapper tenantMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
    }

    public TenantDTO update(UpdateTenantDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var updateTenantProfileCommand = tenantMapper.dtoToCommand(dto);
        return tenantMapper.resultToDto(service.execute(updateTenantProfileCommand, identityCommand));
    }
}
