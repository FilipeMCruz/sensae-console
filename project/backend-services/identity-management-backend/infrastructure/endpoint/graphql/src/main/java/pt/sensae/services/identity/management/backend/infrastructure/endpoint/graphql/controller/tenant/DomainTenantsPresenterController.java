package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;
import pt.sensae.services.identity.management.backend.application.service.tenant.ViewTenantsInDomainService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain.ViewDomainDTOImpl;

import java.util.List;

@DgsComponent
public class DomainTenantsPresenterController {

    private final ViewTenantsInDomainService service;

    public DomainTenantsPresenterController(ViewTenantsInDomainService service) {
        this.service = service;
    }

    @DgsQuery(field = "viewTenantsInDomain")
    public List<TenantDTO> fetch(@InputArgument(value = "domain") ViewDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.fetch(info, AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
