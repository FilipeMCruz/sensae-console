package pt.sensae.services.identity.management.backend.application.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.ChangeDomainDTO;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainDTO;
import pt.sensae.services.identity.management.backend.domainservices.service.domain.ChangeDomain;

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
