package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.device;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.application.service.device.PlaceDeviceInDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device.ExpelDeviceFromDomainDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.device.PlaceDeviceInDomainDTOImpl;

@DgsComponent
public class DeviceTransporterController {

    private final PlaceDeviceInDomainService service;

    public DeviceTransporterController(PlaceDeviceInDomainService service) {
        this.service = service;
    }

    @DgsMutation(field = "addDevice")
    public DeviceIdDTO addDevice(@InputArgument(value = "instructions") PlaceDeviceInDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.place(info, AuthMiddleware.buildAccessToken(auth));
    }

    @DgsMutation(field = "removeDevice")
    public DeviceIdDTO removeDevice(@InputArgument(value = "instructions") ExpelDeviceFromDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.expel(info, AuthMiddleware.buildAccessToken(auth));
    }
}
