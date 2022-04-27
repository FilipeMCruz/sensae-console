package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.services.data.DeviceLatestDataCollectorService;
import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.LatestDataQueryFilters;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class DeviceLatestDataCollectorQuery {

    private final DeviceLatestDataCollectorService service;

    public DeviceLatestDataCollectorQuery(DeviceLatestDataCollectorService service) {
        this.service = service;
    }

    @DgsQuery
    public List<SensorDataDTO> fetchLatestData(@InputArgument("filters") LatestDataQueryFilters filters, @RequestHeader("Authorization") String auth) {
        return service.fetch(filters.gardens.stream(),
                        filters.devices.stream(),
                        AuthMiddleware.buildAccessToken(auth))
                .collect(Collectors.toList());
    }
}
