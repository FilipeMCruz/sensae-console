package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import sharespot.services.locationtrackingbackend.application.GPSLatestSensorData;
import sharespot.services.locationtrackingbackend.domain.model.livedata.SensorData;

import java.util.List;

@DgsComponent
public class GPSLatestSensorDataQuery {

    private final GPSLatestSensorData collector;

    public GPSLatestSensorDataQuery(GPSLatestSensorData collector) {
        this.collector = collector;
    }

    @DgsQuery
    public List<SensorData> latest() {
        return collector.latest();
    }
}