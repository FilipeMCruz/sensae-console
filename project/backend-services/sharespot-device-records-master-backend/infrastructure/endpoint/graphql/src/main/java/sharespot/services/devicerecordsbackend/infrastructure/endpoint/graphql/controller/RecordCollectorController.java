package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.devicerecordsbackend.application.DeviceRecordDTO;
import sharespot.services.devicerecordsbackend.application.RecordCollectorService;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class RecordCollectorController {

    private final RecordCollectorService service;

    public RecordCollectorController(RecordCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "deviceRecords")
    public List<DeviceRecordDTO> collect(@RequestHeader("Authorization") String auth) {
        return service.records(AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
