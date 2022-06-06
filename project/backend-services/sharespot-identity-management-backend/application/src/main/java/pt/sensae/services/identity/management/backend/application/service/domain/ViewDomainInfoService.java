package pt.sensae.services.identity.management.backend.application.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.mapper.MainMapper;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainInfoDTO;
import pt.sensae.services.identity.management.backend.application.model.domain.ViewDomainDTO;
import pt.sensae.services.identity.management.backend.domainservices.service.domain.ViewDomainInfo;

@Service
public class ViewDomainInfoService {

    private final ViewDomainInfo service;

    private final TenantMapper tenantMapper;

    private final DomainMapper domainMapper;

    private final MainMapper mainMapper;

    public ViewDomainInfoService(ViewDomainInfo service, TenantMapper tenantMapper, DomainMapper domainMapper, MainMapper mainMapper) {
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.domainMapper = domainMapper;
        this.mainMapper = mainMapper;
    }

    public DomainInfoDTO fetch(ViewDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var createDomainCommand = domainMapper.dtoToCommand(dto);
        var result = service.fetch(createDomainCommand, identityCommand);
        return mainMapper.resultToDto(result);
    }
}
