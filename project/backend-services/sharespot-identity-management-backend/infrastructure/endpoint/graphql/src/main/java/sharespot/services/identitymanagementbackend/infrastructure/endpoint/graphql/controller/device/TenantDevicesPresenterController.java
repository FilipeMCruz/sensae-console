package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.device;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.application.service.device.ViewTenantDevicesService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;

import java.util.List;

@DgsComponent
public class TenantDevicesPresenterController {

    private final ViewTenantDevicesService service;

    public TenantDevicesPresenterController(ViewTenantDevicesService service) {
        this.service = service;
    }

    @DgsQuery(field = "viewTenantDevices")
    public List<DeviceIdDTO> fetch(@RequestHeader("Authorization") String auth) {
        return service.fetch(AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
