package pt.sensae.services.device.management.slave.backend.application;

import pt.sensae.services.device.management.slave.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.slave.backend.domain.model.DeviceWithSubDevices;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

public interface SensorDataWithDeviceInformationMapper {

    DeviceWithSubDevices domainToDto(SensorDataDTO domain, DeviceInformation records);
}
