package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;
import sharespot.services.identitymanagementbackend.application.service.tenant.AuthenticateTenantService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;

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
