package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;
import pt.sensae.services.identity.management.backend.application.service.tenant.UpdateTenantProfileService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant.UpdateTenantDTOImpl;

@DgsComponent
public class TenantProfileUpdaterController {

    private final UpdateTenantProfileService service;

    public TenantProfileUpdaterController(UpdateTenantProfileService service) {
        this.service = service;
    }
    
    @DgsMutation(field = "updateTenant")
    public TenantDTO updateTenant(@InputArgument(value = "instructions") UpdateTenantDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.update(info, AuthMiddleware.buildAccessToken(auth));
    }
}
