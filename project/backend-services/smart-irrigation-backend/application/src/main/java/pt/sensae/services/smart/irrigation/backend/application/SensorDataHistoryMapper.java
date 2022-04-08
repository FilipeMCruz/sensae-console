package pt.sensae.services.smart.irrigation.backend.application;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;

public interface SensorDataHistoryMapper {

    SensorDataHistoryDTO toDto(DeviceWithData dto);
}
