package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.device.management.master.backend.application.DeviceRecordDTO;
import pt.sensae.services.device.management.master.backend.application.RecordCollectorService;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class RecordCollectorController {

    private final RecordCollectorService service;

    public RecordCollectorController(RecordCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "deviceRecords")
    public List<DeviceRecordDTO> collect(@RequestHeader("Authorization") String auth) {
        return service.catalog(AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
