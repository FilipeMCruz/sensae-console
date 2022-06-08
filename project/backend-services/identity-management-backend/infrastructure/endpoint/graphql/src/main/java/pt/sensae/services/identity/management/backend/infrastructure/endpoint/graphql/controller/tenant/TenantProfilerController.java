package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;
import pt.sensae.services.identity.management.backend.application.service.tenant.AuthenticateTenantService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;

@DgsComponent
public class TenantProfilerController {

    private final AuthenticateTenantService service;

    public TenantProfilerController(AuthenticateTenantService service) {
        this.service = service;
    }

    @DgsQuery(field = "profile")
    public TenantDTO profile(@RequestHeader("Authorization") String auth) {
        return service.fetch(AuthMiddleware.buildAccessToken(auth));
    }
}
