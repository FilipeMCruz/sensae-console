package sharespot.services.identitymanagementbackend.application.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.ChangeDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.domain.ChangeDomain;

@Service
public class ChangeDomainService {

    private final ChangeDomain service;

    private final DomainMapper domainMapper;

    private final TenantMapper tenantMapper;

    public ChangeDomainService(ChangeDomain service, DomainMapper domainMapper, TenantMapper tenantMapper) {
        this.service = service;
        this.domainMapper = domainMapper;
        this.tenantMapper = tenantMapper;
    }

    public DomainDTO change(ChangeDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var command = domainMapper.dtoToCommand(dto);
        var result = service.execute(command, identityCommand);
        return domainMapper.resultToDto(result);
    }
}