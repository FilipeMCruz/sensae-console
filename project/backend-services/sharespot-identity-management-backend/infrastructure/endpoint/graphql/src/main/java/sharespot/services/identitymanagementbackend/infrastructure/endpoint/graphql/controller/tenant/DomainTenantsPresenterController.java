package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;
import sharespot.services.identitymanagementbackend.application.service.tenant.ViewTenantsInDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.ViewDomainDTOImpl;

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
