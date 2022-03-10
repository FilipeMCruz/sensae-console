package sharespot.services.identitymanagementbackend.application.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.ViewDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.ViewTenantsInDomain;

import java.util.stream.Stream;

@Service
public class ViewTenantsInDomainService {

    private final ViewTenantsInDomain service;

    private final TenantMapper tenantMapper;

    private final DomainMapper domainMapper;

    public ViewTenantsInDomainService(ViewTenantsInDomain service, TenantMapper tenantMapper, DomainMapper domainMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.domainMapper = domainMapper;
    }

    public Stream<TenantDTO> fetch(ViewDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = domainMapper.dtoToCommand(dto);
        return service.fetch(createDomainCommand, identityCommand)
                .map(tenantMapper::resultToDto);
    }
}
