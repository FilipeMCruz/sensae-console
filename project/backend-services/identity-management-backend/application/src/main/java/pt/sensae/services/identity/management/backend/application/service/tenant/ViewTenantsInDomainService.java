package pt.sensae.services.identity.management.backend.application.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.ViewDomainDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;
import pt.sensae.services.identity.management.backend.domainservices.service.tenant.ViewTenantsInDomain;

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
