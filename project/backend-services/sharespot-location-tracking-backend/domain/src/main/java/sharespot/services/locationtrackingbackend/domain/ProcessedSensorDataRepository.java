package sharespot.services.locationtrackingbackend.domain;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataFilter;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataQuery;

import java.util.List;

public interface ProcessedSensorDataRepository {

    void insert(ProcessedSensorDataWithRecordsDTO dao);

    GPSSensorDataHistory queryDevice(GPSSensorDataFilter filters);
    
    List<ProcessedSensorDataWithRecordsDTO> lastDataOfEachDevice();
    
}
