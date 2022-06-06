package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.service.tenant.AuthenticateTenantService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;

@DgsComponent
public class TenantAuthenticatorController {

    private final AuthenticateTenantService service;

    public TenantAuthenticatorController(AuthenticateTenantService service) {
        this.service = service;
    }

    @DgsQuery(field = "authenticate")
    public AccessTokenDTO authenticate(@InputArgument(value = "provider") String provider, @RequestHeader("Authorization") String auth) {
        return service.authenticate(provider, AuthMiddleware.buildIdentityToken(auth));
    }
}
