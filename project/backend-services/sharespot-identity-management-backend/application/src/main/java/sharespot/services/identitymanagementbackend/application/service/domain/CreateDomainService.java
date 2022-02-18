package sharespot.services.identitymanagementbackend.application.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;
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

    public void create(DomainDTO dto, JWTTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = domainMapper.dtoToCommand(dto);
        service.execute(createDomainCommand, identityCommand);
    }
}
