package sharespot.services.fleetmanagementbackend.domain.model.pastdata;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class GPSSensorDataFilter {

    public List<UUID> devices;
    public Timestamp startTime;
    public Timestamp endTime;
}
