package sharespot.services.identitymanagementbackend.application.service.domain;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.MainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainInfoDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.ViewDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainResult;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.ViewDomainQuery;
import sharespot.services.identitymanagementbackend.domainservices.service.domain.ViewDomainInfo;
import sharespot.services.identitymanagementbackend.domainservices.service.domain.ViewDomains;

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

    //TODO: try to do this in a single query
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
