package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import sharespot.services.devicerecordsbackend.application.DeviceRecordDTO;
import sharespot.services.devicerecordsbackend.application.RecordRegisterService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model.DeviceRecordDTOImpl;

@DgsComponent
public class RecordRegisterController {

    private final RecordRegisterService service;

    public RecordRegisterController(RecordRegisterService service) {
        this.service = service;
    }

    @DgsMutation(field = "index")
    public DeviceRecordDTO index(@InputArgument(value = "records") DeviceRecordDTOImpl dto) {
        return service.register(dto);
    }
}
