package pt.sensae.services.smart.irrigation.backend.application.mapper.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorReadingDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceWithData;
import pt.sharespot.iot.core.data.model.DataUnitDTO;

public interface SensorDataMapper {

    SensorReadingDTO toDto(DataUnitDTO dto);

    SensorReadingDTO toDto(DeviceWithData dto);
}
