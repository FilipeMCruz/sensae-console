package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.domain;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainInfoDTO;
import pt.sensae.services.identity.management.backend.application.service.domain.ViewDomainInfoService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain.ViewDomainDTOImpl;

@DgsComponent
public class DomainInfoPresenterController {

    private final ViewDomainInfoService service;

    public DomainInfoPresenterController(ViewDomainInfoService service) {
        this.service = service;
    }

    @DgsQuery(field = "viewDomainInfo")
    public DomainInfoDTO fetch(@InputArgument(value = "domain") ViewDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.fetch(info, AuthMiddleware.buildAccessToken(auth));
    }
}
