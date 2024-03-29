package pt.sensae.services.smart.irrigation.backend.application.mapper.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorDataHistoryDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;

public interface SensorDataHistoryMapper {

    SensorDataHistoryDTO toDto(DeviceWithData dto);
}
