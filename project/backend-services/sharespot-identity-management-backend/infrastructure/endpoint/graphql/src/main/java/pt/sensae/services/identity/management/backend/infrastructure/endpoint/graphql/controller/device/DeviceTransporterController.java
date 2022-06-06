package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.device;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.device.DeviceIdDTO;
import pt.sensae.services.identity.management.backend.application.service.device.PlaceDeviceInDomainService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device.ExpelDeviceFromDomainDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.device.PlaceDeviceInDomainDTOImpl;

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
