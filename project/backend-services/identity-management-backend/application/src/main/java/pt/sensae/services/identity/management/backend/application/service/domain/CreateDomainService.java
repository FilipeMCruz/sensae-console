package pt.sensae.services.identity.management.backend.application.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.CreateDomainDTO;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainDTO;
import pt.sensae.services.identity.management.backend.domainservices.service.domain.CreateDomain;

@Service
public class CreateDomainService {

    private final CreateDomain service;

    private final DomainMapper domainMapper;

    private final TenantMapper tenantMapper;

    public CreateDomainService(CreateDomain service, DomainMapper domainMapper, TenantMapper tenantMapper) {
        this.service = service;
        this.domainMapper = domainMapper;
        this.tenantMapper = tenantMapper;
    }

    public DomainDTO create(CreateDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = domainMapper.dtoToCommand(dto);
        var result = service.execute(createDomainCommand, identityCommand);
        return domainMapper.resultToDto(result);
    }
}
