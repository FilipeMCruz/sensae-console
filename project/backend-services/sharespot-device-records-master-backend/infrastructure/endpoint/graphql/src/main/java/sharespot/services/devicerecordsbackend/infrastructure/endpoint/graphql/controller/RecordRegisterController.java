package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.devicerecordsbackend.application.DeviceRecordDTO;
import sharespot.services.devicerecordsbackend.application.RecordRegisterService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model.DeviceRecordDTOImpl;

@DgsComponent
public class RecordRegisterController {

    private final RecordRegisterService service;

    public RecordRegisterController(RecordRegisterService service) {
        this.service = service;
    }

    @DgsMutation(field = "index")
    public DeviceRecordDTO index(@InputArgument(value = "records") DeviceRecordDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.register(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
