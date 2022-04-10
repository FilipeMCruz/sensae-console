package pt.sensae.services.smart.irrigation.backend.application.mapper;

import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

public interface SensorDataMapper {

    SensorDataDTO toDto(ProcessedSensorDataDTO dto);
}
