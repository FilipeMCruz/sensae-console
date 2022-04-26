package pt.sensae.services.device.management.slave.backend.application;

import pt.sensae.services.device.management.slave.backend.domain.model.DeviceWithSubDevices;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceRecords;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

public interface ProcessedSensorDataWithRecordMapper {

    DeviceWithSubDevices domainToDto(ProcessedSensorDataDTO domain, DeviceRecords records);
}