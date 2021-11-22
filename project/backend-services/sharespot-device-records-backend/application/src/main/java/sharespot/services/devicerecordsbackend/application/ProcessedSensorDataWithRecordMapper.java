package sharespot.services.devicerecordsbackend.application;

import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorDataWithRecord;

public interface ProcessedSensorDataWithRecordMapper {

    ProcessedSensorDataWithRecordDTO domainToDto(ProcessedSensorDataWithRecord domain);
}
