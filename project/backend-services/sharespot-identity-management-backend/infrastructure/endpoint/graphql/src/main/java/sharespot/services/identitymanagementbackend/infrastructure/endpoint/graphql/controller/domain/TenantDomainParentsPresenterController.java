package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.domain;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainIdDTO;
import sharespot.services.identitymanagementbackend.application.service.device.ViewTenantDevicesService;
import sharespot.services.identitymanagementbackend.application.service.domain.ViewTenantDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;

import java.util.List;

@DgsComponent
public class TenantDomainParentsPresenterController {

    private final ViewTenantDomainService service;

    public TenantDomainParentsPresenterController(ViewTenantDomainService service) {
        this.service = service;
    }

    @DgsQuery(field = "viewTenantParentDomains")
    public List<DomainIdDTO> fetch(@RequestHeader("Authorization") String auth) {
        return service.fetch(AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
