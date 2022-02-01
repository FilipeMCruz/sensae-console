package sharespot.services.locationtrackingbackend.domain.model.pastdata;

import java.util.ArrayList;
import java.util.List;

public class GPSSensorDataHistory {

    public String deviceId;
    public String deviceName;
    public List<GPSSensorDataHistorySegment> segments;
    public Long startTime;
    public Long endTime;
    public Double distance;

    public GPSSensorDataHistory() {
        segments = new ArrayList<>();
    }
}
