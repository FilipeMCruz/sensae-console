package sharespot.services.identitymanagementbackend.application.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.ViewDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.domain.ViewDomains;

import java.util.stream.Stream;

@Service
public class ViewDomainsService {

    private final ViewDomains service;

    private final TenantMapper tenantMapper;

    private final DomainMapper domainMapper;

    public ViewDomainsService(ViewDomains service, TenantMapper tenantMapper, DomainMapper domainMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.domainMapper = domainMapper;
    }

    public Stream<DomainDTO> fetch(ViewDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = domainMapper.dtoToCommand(dto);
        return service.fetch(createDomainCommand, identityCommand)
                .map(domainMapper::resultToDto);
    }
}
