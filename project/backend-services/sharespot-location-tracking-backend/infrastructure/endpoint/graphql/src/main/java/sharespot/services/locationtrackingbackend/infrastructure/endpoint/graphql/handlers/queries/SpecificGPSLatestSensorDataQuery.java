package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import sharespot.services.locationtrackingbackend.application.GPSLatestSensorData;
import sharespot.services.locationtrackingbackend.domain.model.livedata.SensorData;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class SpecificGPSLatestSensorDataQuery {

    private final GPSLatestSensorData collector;

    public SpecificGPSLatestSensorDataQuery(GPSLatestSensorData collector) {
        this.collector = collector;
    }

    @DgsQuery
    public List<SensorData> latestByDevice(@InputArgument("devices") List<String> devices) {
        return collector.latest(devices);
    }
}
