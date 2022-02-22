package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.device;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;
import sharespot.services.identitymanagementbackend.application.model.device.NewDomainForDeviceDTO;
import sharespot.services.identitymanagementbackend.application.service.device.PlaceDeviceInDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;

@DgsComponent
public class DeviceTransporterController {

    private final PlaceDeviceInDomainService service;

    public DeviceTransporterController(PlaceDeviceInDomainService service) {
        this.service = service;
    }

    @DgsMutation(field = "addDevice")
    public DeviceDTO addDevice(@InputArgument(value = "instructions") NewDomainForDeviceDTO info, @RequestHeader("Authorization") String auth) {
        return service.place(info, AuthMiddleware.buildAccessToken(auth));
    }
}
