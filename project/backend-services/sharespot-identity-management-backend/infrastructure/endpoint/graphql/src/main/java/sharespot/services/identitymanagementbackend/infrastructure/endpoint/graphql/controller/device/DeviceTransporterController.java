package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.device;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.device.NewDomainForDeviceDTO;
import sharespot.services.identitymanagementbackend.application.service.device.PlaceDeviceInDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AccessTokenDTOImpl;

@DgsComponent
public class DeviceTransporterController {

    private final PlaceDeviceInDomainService service;

    public DeviceTransporterController(PlaceDeviceInDomainService service) {
        this.service = service;
    }

    @DgsMutation(field = "moveDevice")
    public boolean moveDevice(@InputArgument(value = "instructions") NewDomainForDeviceDTO info, @RequestHeader("Authorization") String auth) {
        var accessTokenDTO = new AccessTokenDTOImpl();
        accessTokenDTO.token = auth.substring(7);
        service.place(info, accessTokenDTO);
        return true;
    }
}
