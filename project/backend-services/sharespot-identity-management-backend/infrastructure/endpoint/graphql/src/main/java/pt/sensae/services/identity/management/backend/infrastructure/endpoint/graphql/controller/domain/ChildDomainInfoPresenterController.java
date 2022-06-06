package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.domain;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainInfoDTO;
import pt.sensae.services.identity.management.backend.application.service.domain.ViewChildDomainsInfoService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain.ViewDomainDTOImpl;

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
