package pt.sensae.services.smart.irrigation.backend.application.mapper.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorReadingDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

public interface SensorDataMapper {

    SensorReadingDTO toDto(SensorDataDTO dto);

    SensorReadingDTO toDto(DeviceWithData dto);
}
