package sharespot.services.locationtrackingbackend.domain;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataFilter;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataQuery;

public interface ProcessedSensorDataRepository {

    void insert(ProcessedSensorDataWithRecordsDTO dao);

    GPSSensorDataHistory queryDevice(GPSSensorDataFilter filters);
}
