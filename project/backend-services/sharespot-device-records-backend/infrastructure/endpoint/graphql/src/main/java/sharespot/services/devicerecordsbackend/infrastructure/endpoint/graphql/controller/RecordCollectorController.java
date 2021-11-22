package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import sharespot.services.devicerecordsbackend.application.DeviceRecordDTO;
import sharespot.services.devicerecordsbackend.application.RecordCollectorService;

import java.util.Set;

@DgsComponent
public class RecordCollectorController {

    private final RecordCollectorService service;

    public RecordCollectorController(RecordCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "deviceRecords")
    public Set<DeviceRecordDTO> collect() {
        return service.records();
    }
}
