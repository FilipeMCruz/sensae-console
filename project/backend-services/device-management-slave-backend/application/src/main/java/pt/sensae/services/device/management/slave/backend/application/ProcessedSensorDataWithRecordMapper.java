package pt.sensae.services.device.management.slave.backend.application;

import pt.sensae.services.device.management.slave.backend.domain.model.DeviceWithSubDevices;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceInformation;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

public interface ProcessedSensorDataWithRecordMapper {

    DeviceWithSubDevices domainToDto(SensorDataDTO domain, DeviceInformation records);
}
