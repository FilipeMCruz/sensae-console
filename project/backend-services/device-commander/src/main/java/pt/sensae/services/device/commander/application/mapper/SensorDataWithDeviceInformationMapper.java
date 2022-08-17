package pt.sensae.services.device.commander.application.mapper;

import pt.sensae.services.device.commander.domain.DeviceInformation;
import pt.sensae.services.device.commander.domain.DeviceWithSubDevices;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

public interface SensorDataWithDeviceInformationMapper {

    DeviceWithSubDevices domainToDto(SensorDataDTO domain, DeviceInformation records);
}
