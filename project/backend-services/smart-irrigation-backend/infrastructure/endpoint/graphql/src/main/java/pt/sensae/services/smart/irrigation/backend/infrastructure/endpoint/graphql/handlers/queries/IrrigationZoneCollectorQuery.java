package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.services.irrigationZone.FetchIrrigationZoneService;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class IrrigationZoneCollectorQuery {

    private final FetchIrrigationZoneService service;

    public IrrigationZoneCollectorQuery(FetchIrrigationZoneService service) {
        this.service = service;
    }

    @DgsQuery
    public List<IrrigationZoneDTO> fetchIrrigationZones(@RequestHeader("Authorization") String auth) {
        return service.fetchAll(AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
