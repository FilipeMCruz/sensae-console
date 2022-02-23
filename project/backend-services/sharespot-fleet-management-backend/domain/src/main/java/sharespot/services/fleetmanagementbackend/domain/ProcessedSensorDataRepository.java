package sharespot.services.fleetmanagementbackend.domain;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.fleetmanagementbackend.domain.model.pastdata.GPSSensorDataFilter;

import java.util.List;

public interface ProcessedSensorDataRepository {

    void insert(ProcessedSensorDataDTO dao);

    List<ProcessedSensorDataDTO> queryMultipleDevices(GPSSensorDataFilter filters);
    
    List<ProcessedSensorDataDTO> lastDataOfEachDevice();

    List<ProcessedSensorDataDTO> queryPastData(ProcessedSensorDataDTO dao, Integer timeSpanInMinutes);
}
