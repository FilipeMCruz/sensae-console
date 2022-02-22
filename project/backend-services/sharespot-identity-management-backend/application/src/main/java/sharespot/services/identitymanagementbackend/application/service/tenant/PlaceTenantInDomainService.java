package sharespot.services.identitymanagementbackend.application.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.NewDomainForTenantDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.PlaceTenantInDomain;

@Service
public class PlaceTenantInDomainService {

    private final PlaceTenantInDomain service;

    private final TenantMapper tenantMapper;

    public PlaceTenantInDomainService(PlaceTenantInDomain service, TenantMapper tenantMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
    }

    public TenantDTO place(NewDomainForTenantDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = tenantMapper.dtoToCommand(dto);
        return tenantMapper.resultToDto(service.execute(createDomainCommand, identityCommand));
    }
}
