package sharespot.services.locationtrackingbackend.domain.model.pastdata;

import java.sql.Timestamp;
import java.time.Instant;

public class GPSSensorDataQuery {

    public String device;
    public String startTime;
    public String endTime;

    public GPSSensorDataFilter toFilter() {
        var filter = new GPSSensorDataFilter();
        filter.device = device;
        filter.startTime = Timestamp.from(Instant.ofEpochSecond(Long.parseLong(startTime)));
        filter.endTime = Timestamp.from(Instant.ofEpochSecond(Long.parseLong(endTime)));
        return filter;
    }
}
