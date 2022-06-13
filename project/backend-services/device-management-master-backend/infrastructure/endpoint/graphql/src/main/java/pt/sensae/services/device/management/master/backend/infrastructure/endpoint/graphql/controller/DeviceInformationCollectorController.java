package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationDTO;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationCollectorService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class DeviceInformationCollectorController {

    private final DeviceInformationCollectorService service;

    public DeviceInformationCollectorController(DeviceInformationCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "deviceInformation")
    public List<DeviceInformationDTO> deviceInformation(@RequestHeader("Authorization") String auth) {
        return service.catalog(AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
