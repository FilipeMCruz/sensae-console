package sharespot.services.identitymanagementbackend.application.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.MainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainInfoDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.ViewDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.domain.ViewDomainInfo;

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
