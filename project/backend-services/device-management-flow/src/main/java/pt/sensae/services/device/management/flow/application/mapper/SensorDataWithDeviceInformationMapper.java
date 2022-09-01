package pt.sensae.services.device.management.flow.application.mapper;

import pt.sensae.services.device.management.flow.domain.DeviceInformation;
import pt.sensae.services.device.management.flow.domain.DeviceWithSubDevices;
import pt.sharespot.iot.core.data.model.DataUnitDTO;

public interface SensorDataWithDeviceInformationMapper {

    DeviceWithSubDevices domainToDto(DataUnitDTO domain, DeviceInformation records);
}
