package sharespot.services.locationtrackingbackend.domain;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataFilter;

import java.util.List;

public interface ProcessedSensorDataRepository {

    void insert(ProcessedSensorDataWithRecordsDTO dao);

    List<ProcessedSensorDataWithRecordsDTO> queryMultipleDevices(GPSSensorDataFilter filters);
    
    List<ProcessedSensorDataWithRecordsDTO> lastDataOfEachDevice();

    List<ProcessedSensorDataWithRecordsDTO> queryPastData(ProcessedSensorDataWithRecordsDTO dao, Integer timeSpanInMinutes);
}
