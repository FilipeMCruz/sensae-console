package sharespot.services.fleetmanagementbackend.domain;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.fleetmanagementbackend.domain.model.pastdata.GPSSensorDataFilter;

import java.util.List;

public interface ProcessedSensorDataRepository {

    void insert(ProcessedSensorDataWithRecordsDTO dao);

    List<ProcessedSensorDataWithRecordsDTO> queryMultipleDevices(GPSSensorDataFilter filters);
    
    List<ProcessedSensorDataWithRecordsDTO> lastDataOfEachDevice();

    List<ProcessedSensorDataWithRecordsDTO> queryPastData(ProcessedSensorDataWithRecordsDTO dao, Integer timeSpanInMinutes);
}
