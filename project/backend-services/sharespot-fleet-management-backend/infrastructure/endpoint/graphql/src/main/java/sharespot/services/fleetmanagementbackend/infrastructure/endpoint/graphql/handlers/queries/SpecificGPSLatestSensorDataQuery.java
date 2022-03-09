package sharespot.services.fleetmanagementbackend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.fleetmanagementbackend.application.GPSLatestSensorData;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.SensorData;
import sharespot.services.fleetmanagementbackend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class SpecificGPSLatestSensorDataQuery {

    private final GPSLatestSensorData collector;

    public SpecificGPSLatestSensorDataQuery(GPSLatestSensorData collector) {
        this.collector = collector;
    }

    @DgsQuery
    public List<SensorData> latestByDevice(@InputArgument("devices") List<String> devices, @RequestHeader("Authorization") String auth) {
        return collector.latest(devices, AuthMiddleware.buildAccessToken(auth));
    }
}
