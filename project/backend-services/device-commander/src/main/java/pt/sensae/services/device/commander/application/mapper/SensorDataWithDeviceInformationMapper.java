package pt.sensae.services.device.commander.application.mapper;

import pt.sensae.services.device.commander.domain.DeviceInformation;
import pt.sensae.services.device.commander.domain.DeviceWithSubDevices;
import pt.sharespot.iot.core.data.model.DataUnitDTO;

public interface SensorDataWithDeviceInformationMapper {

    DeviceWithSubDevices domainToDto(DataUnitDTO domain, DeviceInformation records);
}
