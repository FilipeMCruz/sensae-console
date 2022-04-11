package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.FetchGardeningAreasService;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class GardeningAreaCollectorQuery {

    private final FetchGardeningAreasService service;

    public GardeningAreaCollectorQuery(FetchGardeningAreasService service) {
        this.service = service;
    }

    @DgsQuery
    public List<GardeningAreaDTO> fetch(@RequestHeader("Authorization") String auth) {
        return service.fetchAll(AuthMiddleware.buildAccessToken(auth)).toList();
    }
}