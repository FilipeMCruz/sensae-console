package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.service.tenant.AuthenticateTenantService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;

@DgsComponent
public class TenantAuthenticationRefresherController {

    private final AuthenticateTenantService service;

    public TenantAuthenticationRefresherController(AuthenticateTenantService service) {
        this.service = service;
    }

    @DgsQuery(field = "refresh")
    public AccessTokenDTO refresh(@RequestHeader("Authorization") String auth) {
        return service.authenticate(AuthMiddleware.buildAccessToken(auth));
    }
}
