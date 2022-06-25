package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.device.management.master.backend.application.DeviceCommandEmitterService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model.CommandDTOImpl;

@DgsComponent
public class DeviceCommanderController {

    private final DeviceCommandEmitterService service;

    public DeviceCommanderController(DeviceCommandEmitterService service) {
        this.service = service;
    }

    @DgsMutation(field = "deviceCommand")
    public CommandDTOImpl deviceCommand(@InputArgument(value = "instructions") CommandDTOImpl dto, @RequestHeader("Authorization") String auth) {
        service.command(dto, AuthMiddleware.buildAccessToken(auth));
        return dto;
    }
}
