package pt.sensae.services.identity.management.backend.application.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainIdDTO;
import pt.sensae.services.identity.management.backend.domainservices.service.tenant.ViewTenantParentDomains;

import java.util.stream.Stream;

@Service
public class ViewTenantDomainService {

    private final ViewTenantParentDomains service;

    private final TenantMapper tenantMapper;

    private final DomainMapper domainMapper;

    public ViewTenantDomainService(ViewTenantParentDomains service, TenantMapper tenantMapper, DomainMapper domainMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.domainMapper = domainMapper;
    }

    public Stream<DomainIdDTO> fetch(AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        return service.fetch(identityCommand)
                .flatMap(domainMapper::resultToSimpleDto);
    }
}
