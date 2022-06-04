package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;
import sharespot.services.identitymanagementbackend.application.service.tenant.PlaceTenantInDomainService;
import sharespot.services.identitymanagementbackend.application.service.tenant.UpdateTenantProfileService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.ExpelTenantFromDomainDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.PlaceTenantInDomainDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.UpdateTenantDTOImpl;

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
