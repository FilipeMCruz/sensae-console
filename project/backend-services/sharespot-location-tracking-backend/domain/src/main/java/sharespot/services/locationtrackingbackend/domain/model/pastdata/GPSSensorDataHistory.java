package sharespot.services.locationtrackingbackend.domain.model.pastdata;

import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;

import java.util.ArrayList;
import java.util.List;

public class GPSSensorDataHistory {

    public String deviceId;
    public String deviceName;
    public List<GPSDataDetails> data;
    public Long startTime;
    public Long endTime;

    public GPSSensorDataHistory() {
        data = new ArrayList<>();
    }
}
