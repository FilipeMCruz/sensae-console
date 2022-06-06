package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.domain;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainDTO;
import pt.sensae.services.identity.management.backend.application.service.domain.ViewDomainsService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain.ViewDomainDTOImpl;

import java.util.List;

@DgsComponent
public class DomainPresenterController {

    private final ViewDomainsService service;

    public DomainPresenterController(ViewDomainsService service) {
        this.service = service;
    }

    @DgsQuery(field = "viewDomain")
    public List<DomainDTO> fetch(@InputArgument(value = "domain") ViewDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.fetch(info, AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
