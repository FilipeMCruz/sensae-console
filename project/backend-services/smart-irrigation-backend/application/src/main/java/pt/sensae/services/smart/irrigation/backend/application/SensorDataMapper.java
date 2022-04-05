package pt.sensae.services.smart.irrigation.backend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

public interface SensorDataMapper {

    SensorDataDTO toDto(ProcessedSensorDataDTO dto);
}
