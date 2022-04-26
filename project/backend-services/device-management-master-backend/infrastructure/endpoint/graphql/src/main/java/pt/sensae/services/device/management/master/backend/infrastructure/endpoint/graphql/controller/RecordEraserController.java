package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.device.management.master.backend.application.DeviceDTO;
import pt.sensae.services.device.management.master.backend.application.RecordEraserService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model.DeviceDTOImpl;

@DgsComponent
public class RecordEraserController {

    private final RecordEraserService service;

    public RecordEraserController(RecordEraserService service) {
        this.service = service;
    }

    @DgsMutation(field = "delete")
    public DeviceDTO delete(@InputArgument(value = "device") DeviceDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.erase(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
