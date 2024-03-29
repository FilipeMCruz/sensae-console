package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.services.data.DeviceHistoryDataCollectorService;
import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataHistoryDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.HistoryQueryFiltersDTOImpl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class DeviceHistoryCollectorQuery {

    private final DeviceHistoryDataCollectorService service;

    public DeviceHistoryCollectorQuery(DeviceHistoryDataCollectorService service) {
        this.service = service;
    }

    @DgsQuery
    public List<SensorDataHistoryDTO> history(@InputArgument("filters") HistoryQueryFiltersDTOImpl filters, @RequestHeader("Authorization") String auth) {
        return service.fetch(filters.irrigationZones.stream(),
                        filters.devices.stream(),
                        Instant.ofEpochSecond(Long.parseLong(filters.startTime)),
                        Instant.ofEpochSecond(Long.parseLong(filters.endTime)),
                        AuthMiddleware.buildAccessToken(auth))
                .collect(Collectors.toList());
    }
}
