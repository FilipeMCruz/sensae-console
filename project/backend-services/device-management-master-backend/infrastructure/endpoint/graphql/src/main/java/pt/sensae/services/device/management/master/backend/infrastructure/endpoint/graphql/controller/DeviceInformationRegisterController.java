package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationDTO;
import pt.sensae.services.device.management.master.backend.application.RecordRegisterService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model.DeviceInformationDTOImpl;

@DgsComponent
public class DeviceInformationRegisterController {

    private final RecordRegisterService service;

    public DeviceInformationRegisterController(RecordRegisterService service) {
        this.service = service;
    }

    @DgsMutation(field = "index")
    public DeviceInformationDTO index(@InputArgument(value = "instructions") DeviceInformationDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.register(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
