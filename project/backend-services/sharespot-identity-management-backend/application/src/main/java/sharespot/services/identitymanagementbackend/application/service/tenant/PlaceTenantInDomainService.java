package sharespot.services.identitymanagementbackend.application.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.NewDomainForTenantDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.PlaceTenantInDomain;

@Service
public class PlaceTenantInDomainService {

    private final PlaceTenantInDomain service;

    private final TenantMapper tenantMapper;

    public PlaceTenantInDomainService(PlaceTenantInDomain service, TenantMapper tenantMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
    }

    public void place(NewDomainForTenantDTO dto, JWTTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = tenantMapper.dtoToCommand(dto);
        service.execute(createDomainCommand, identityCommand);
    }
}