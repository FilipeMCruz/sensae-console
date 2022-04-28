package pt.sensae.services.smart.irrigation.backend.application.mapper.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

public interface SensorDataMapper {

    SensorDataDTO toDto(ProcessedSensorDataDTO dto);

    SensorDataDTO toDto(DeviceWithData dto);
}
