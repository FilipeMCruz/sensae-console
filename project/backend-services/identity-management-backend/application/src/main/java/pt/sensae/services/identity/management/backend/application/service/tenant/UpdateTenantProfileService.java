package pt.sensae.services.identity.management.backend.application.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.tenant.UpdateTenantDTO;
import pt.sensae.services.identity.management.backend.domainservices.service.tenant.UpdateTenantProfile;

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
