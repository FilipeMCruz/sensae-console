package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import sharespot.services.devicerecordsbackend.application.DeviceDTO;
import sharespot.services.devicerecordsbackend.application.DeviceRecordDTO;
import sharespot.services.devicerecordsbackend.application.RecordEraserService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model.DeviceDTOImpl;

import java.util.Set;

@DgsComponent
public class RecordEraserController {

    private final RecordEraserService service;

    public RecordEraserController(RecordEraserService service) {
        this.service = service;
    }

    @DgsMutation(field = "delete")
    public DeviceDTO delete(@InputArgument(value = "device") DeviceDTOImpl dto) {
        return service.erase(dto);
    }
}
