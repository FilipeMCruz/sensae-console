package pt.sensae.services.identity.management.backend.application.service.domain;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.mapper.MainMapper;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainInfoDTO;
import pt.sensae.services.identity.management.backend.application.model.domain.ViewDomainDTO;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainResult;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.ViewDomainQuery;
import pt.sensae.services.identity.management.backend.domainservices.service.domain.ViewDomainInfo;
import pt.sensae.services.identity.management.backend.domainservices.service.domain.ViewDomains;

import java.util.stream.Stream;

@Service
public class ViewChildDomainsInfoService {

    private final ViewDomainInfo infoService;

    private final ViewDomains service;

    private final TenantMapper tenantMapper;

    private final DomainMapper domainMapper;

    private final MainMapper mainMapper;

    public ViewChildDomainsInfoService(ViewDomainInfo infoService, ViewDomains service, TenantMapper tenantMapper, DomainMapper domainMapper, MainMapper mainMapper) {
        this.infoService = infoService;
        this.service = service;
        this.tenantMapper = tenantMapper;
        this.domainMapper = domainMapper;
        this.mainMapper = mainMapper;
    }

    public Stream<DomainInfoDTO> fetch(ViewDomainDTO dto, AccessTokenDTO claims) {
        var identityCommand = tenantMapper.dtoToCommand(claims);
        var query = domainMapper.dtoToCommand(dto);
        return service.fetch(query, identityCommand)
                .filter(d -> isDirectChild(query, d))
                .map(d -> infoService.fetch(ViewDomainQuery.of(d.domainId), identityCommand))
                .map(mainMapper::resultToDto);
    }

    private boolean isDirectChild(ViewDomainQuery query, DomainResult d) {
        return d.path.size() >= 2 && d.path.get(d.path.size() - 2).equals(query.topDomainId.toString());
    }
}
