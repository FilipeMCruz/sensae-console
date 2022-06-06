package pt.sensae.services.identity.management.backend.application.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainDTO;
import pt.sensae.services.identity.management.backend.application.model.domain.ViewDomainDTO;
import pt.sensae.services.identity.management.backend.domainservices.service.domain.ViewDomains;

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
