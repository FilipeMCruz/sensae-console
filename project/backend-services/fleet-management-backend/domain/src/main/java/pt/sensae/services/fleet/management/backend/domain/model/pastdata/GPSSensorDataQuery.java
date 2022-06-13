package pt.sensae.services.fleet.management.backend.domain.model.pastdata;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GPSSensorDataQuery {

    public List<String> device;
    public String startTime;
    public String endTime;

    public GPSSensorDataFilter toFilter() {
        var filter = new GPSSensorDataFilter();
        filter.devices = device.stream().map(UUID::fromString).collect(Collectors.toList());
        filter.startTime = Timestamp.from(Instant.ofEpochSecond(Long.parseLong(startTime)));
        filter.endTime = Timestamp.from(Instant.ofEpochSecond(Long.parseLong(endTime)));
        return filter;
    }
}
