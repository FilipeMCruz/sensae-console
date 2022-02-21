package sharespot.services.identitymanagementbackend.application.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.CreateDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.domain.CreateDomain;

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
