package sharespot.services.identitymanagementbackend.application.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainIdDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.ViewTenantParentDomains;

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
