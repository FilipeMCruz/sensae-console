package pt.sensae.services.device.management.slave.backend.application;

import pt.sensae.services.device.management.slave.backend.domain.model.DeviceWithSubDevices;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceInformation;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;

public interface ProcessedSensorDataWithRecordMapper {

    DeviceWithSubDevices domainToDto(ProcessedSensorDataDTO domain, DeviceInformation records);
}
