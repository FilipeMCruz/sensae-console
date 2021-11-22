package sharespot.services.devicerecordsbackend.application;

import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorData;

public interface ProcessedSensorDataMapper {

    ProcessedSensorData dtoToDomain(ProcessedSensorDataDTO dto);
}
