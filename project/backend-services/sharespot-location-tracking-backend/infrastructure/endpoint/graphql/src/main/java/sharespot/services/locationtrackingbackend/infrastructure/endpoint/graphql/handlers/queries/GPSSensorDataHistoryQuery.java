package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import sharespot.services.locationtrackingbackend.application.GPSDataCollector;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataQuery;

import java.util.List;

@DgsComponent
public class GPSSensorDataHistoryQuery {

    private final GPSDataCollector collector;

    public GPSSensorDataHistoryQuery(GPSDataCollector collector) {
        this.collector = collector;
    }

    @DgsQuery
    public List<GPSSensorDataHistory> history(@InputArgument("filters") GPSSensorDataQuery query) {
        return collector.history(query.toFilter());
    }
}
