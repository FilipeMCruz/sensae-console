package pt.sensae.services.identity.management.backend.application.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.ExpelTenantFromDomainDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.PlaceTenantInDomainDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.domainservices.service.tenant.MoveTenant;

@Service
public class PlaceTenantInDomainService {

    private final MoveTenant service;

    private final TenantMapper tenantMapper;

    public PlaceTenantInDomainService(MoveTenant service, TenantMapper tenantMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
    }

    public TenantDTO place(PlaceTenantInDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = tenantMapper.dtoToCommand(dto);
        return tenantMapper.resultToDto(service.execute(createDomainCommand, identityCommand));
    }

    public TenantDTO expel(ExpelTenantFromDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = tenantMapper.dtoToCommand(dto);
        return tenantMapper.resultToDto(service.execute(createDomainCommand, identityCommand));
    }
}
