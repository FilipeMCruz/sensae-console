package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.domain;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainInfoDTO;
import sharespot.services.identitymanagementbackend.application.service.domain.ViewChildDomainsInfoService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.ViewDomainDTOImpl;

import java.util.List;

@DgsComponent
public class ChildDomainInfoPresenterController {

    private final ViewChildDomainsInfoService service;

    public ChildDomainInfoPresenterController(ViewChildDomainsInfoService service) {
        this.service = service;
    }

    @DgsQuery(field = "viewChildDomainsInfo")
    public List<DomainInfoDTO> fetch(@InputArgument(value = "domain") ViewDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.fetch(info, AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
