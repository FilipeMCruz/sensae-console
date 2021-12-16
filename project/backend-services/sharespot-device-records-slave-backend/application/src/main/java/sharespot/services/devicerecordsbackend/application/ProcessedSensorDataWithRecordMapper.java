package sharespot.services.devicerecordsbackend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorDataWithRecord;

public interface ProcessedSensorDataWithRecordMapper {

    ProcessedSensorDataWithRecordsDTO domainToDto(ProcessedSensorDataWithRecord domain);
}
