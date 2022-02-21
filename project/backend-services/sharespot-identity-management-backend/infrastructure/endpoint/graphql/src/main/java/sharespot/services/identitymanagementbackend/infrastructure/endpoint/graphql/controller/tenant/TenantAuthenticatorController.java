package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.service.tenant.AuthenticateTenantService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AuthenticationDTOImpl;

@DgsComponent
public class TenantAuthenticatorController {

    private final AuthenticateTenantService service;

    public TenantAuthenticatorController(AuthenticateTenantService service) {
        this.service = service;
    }

    @DgsQuery(field = "authenticate")
    public AccessTokenDTO authenticate(@InputArgument(value = "tenant") AuthenticationDTOImpl info) {
        return service.authenticate(info);
    }
}
