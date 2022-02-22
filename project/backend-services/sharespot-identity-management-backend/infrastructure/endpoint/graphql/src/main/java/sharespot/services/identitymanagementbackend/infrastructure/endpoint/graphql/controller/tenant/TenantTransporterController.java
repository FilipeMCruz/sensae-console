package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;
import sharespot.services.identitymanagementbackend.application.service.tenant.PlaceTenantInDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.NewDomainForTenantDTOImpl;

@DgsComponent
public class TenantTransporterController {

    private final PlaceTenantInDomainService service;

    public TenantTransporterController(PlaceTenantInDomainService service) {
        this.service = service;
    }

    @DgsMutation(field = "addTenant")
    public TenantDTO addTenant(@InputArgument(value = "instructions") NewDomainForTenantDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.place(info, AuthMiddleware.buildAccessToken(auth));
    }
}
