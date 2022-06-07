package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.tenant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;
import pt.sensae.services.identity.management.backend.application.service.tenant.PlaceTenantInDomainService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant.ExpelTenantFromDomainDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant.PlaceTenantInDomainDTOImpl;

@DgsComponent
public class TenantTransporterController {

    private final PlaceTenantInDomainService service;

    public TenantTransporterController(PlaceTenantInDomainService service) {
        this.service = service;
    }

    @DgsMutation(field = "addTenant")
    public TenantDTO addTenant(@InputArgument(value = "instructions") PlaceTenantInDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.place(info, AuthMiddleware.buildAccessToken(auth));
    }

    @DgsMutation(field = "removeTenant")
    public TenantDTO removeTenant(@InputArgument(value = "instructions") ExpelTenantFromDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.expel(info, AuthMiddleware.buildAccessToken(auth));
    }
}
