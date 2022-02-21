package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.service.tenant.PlaceTenantInDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AccessTokenDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.NewDomainForTenantDTOImpl;

@DgsComponent
public class TenantTransporterController {

    private final PlaceTenantInDomainService service;

    public TenantTransporterController(PlaceTenantInDomainService service) {
        this.service = service;
    }

    @DgsMutation(field = "moveDevice")
    public boolean moveDevice(@InputArgument(value = "instructions") NewDomainForTenantDTOImpl info, @RequestHeader("Authorization") String auth) {
        var accessTokenDTO = new AccessTokenDTOImpl();
        accessTokenDTO.token = auth.substring(7);
        service.place(info, accessTokenDTO);
        return true;
    }
}
