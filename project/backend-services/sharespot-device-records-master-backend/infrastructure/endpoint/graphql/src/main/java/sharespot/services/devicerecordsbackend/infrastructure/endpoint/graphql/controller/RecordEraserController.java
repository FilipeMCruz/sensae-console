package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.devicerecordsbackend.application.DeviceDTO;
import sharespot.services.devicerecordsbackend.application.RecordEraserService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model.DeviceDTOImpl;

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
