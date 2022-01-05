package sharespot.services.locationtrackingbackend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;

public interface SensorDataMapper {
    
    OutSensorData transform(ProcessedSensorDataWithRecordsDTO dto);
}
