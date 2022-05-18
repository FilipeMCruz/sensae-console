package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationDTO;
import pt.sensae.services.device.management.master.backend.application.RecordRegisterService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model.DeviceRecordDTOImpl;

@DgsComponent
public class RecordRegisterController {

    private final RecordRegisterService service;

    public RecordRegisterController(RecordRegisterService service) {
        this.service = service;
    }

    @DgsMutation(field = "index")
    public DeviceInformationDTO index(@InputArgument(value = "records") DeviceRecordDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.register(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
