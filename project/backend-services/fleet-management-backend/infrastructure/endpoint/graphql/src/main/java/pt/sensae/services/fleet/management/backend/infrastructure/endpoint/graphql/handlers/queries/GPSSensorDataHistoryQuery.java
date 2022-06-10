package pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.fleet.management.backend.application.GPSDataCollector;
import pt.sensae.services.fleet.management.backend.domain.model.pastdata.GPSSensorDataHistory;
import pt.sensae.services.fleet.management.backend.domain.model.pastdata.GPSSensorDataQuery;
import pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class GPSSensorDataHistoryQuery {

    private final GPSDataCollector collector;

    public GPSSensorDataHistoryQuery(GPSDataCollector collector) {
        this.collector = collector;
    }

    @DgsQuery
    public List<GPSSensorDataHistory> history(@InputArgument("filters") GPSSensorDataQuery query, @RequestHeader("Authorization") String auth) {
        return collector.history(query.toFilter(), AuthMiddleware.buildAccessToken(auth));
    }
}