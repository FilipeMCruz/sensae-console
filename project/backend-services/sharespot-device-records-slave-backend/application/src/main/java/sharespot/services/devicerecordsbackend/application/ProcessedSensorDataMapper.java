package sharespot.services.devicerecordsbackend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorData;

public interface ProcessedSensorDataMapper {

    ProcessedSensorData dtoToDomain(ProcessedSensorDataDTO dto);
}
