package pt.sensae.services.device.management.flow.application.mapper;

import pt.sensae.services.device.management.flow.domain.DeviceInformation;
import pt.sensae.services.device.management.flow.domain.DeviceWithSubDevices;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

public interface SensorDataWithDeviceInformationMapper {

    DeviceWithSubDevices domainToDto(SensorDataDTO domain, DeviceInformation records);
}
