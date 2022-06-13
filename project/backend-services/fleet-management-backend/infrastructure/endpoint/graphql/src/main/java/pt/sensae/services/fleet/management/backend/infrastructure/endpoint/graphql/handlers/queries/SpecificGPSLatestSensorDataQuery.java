package pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.fleet.management.backend.application.GPSLatestSensorData;
import pt.sensae.services.fleet.management.backend.domain.model.livedata.SensorData;
import pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class SpecificGPSLatestSensorDataQuery {

    private final GPSLatestSensorData collector;

    public SpecificGPSLatestSensorDataQuery(GPSLatestSensorData collector) {
        this.collector = collector;
    }

    @DgsQuery
    public List<SensorData> latestByDevice(@InputArgument("devices") List<String> devices, @RequestHeader("Authorization") String auth) {
        return collector.latest(devices, AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
