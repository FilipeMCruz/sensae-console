package sharespot.services.devicerecordsbackend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;

public interface ProcessedSensorDataWithRecordMapper {

    ProcessedSensorDataDTO domainToDto(ProcessedSensorDataDTO domain, DeviceRecords records);
}
