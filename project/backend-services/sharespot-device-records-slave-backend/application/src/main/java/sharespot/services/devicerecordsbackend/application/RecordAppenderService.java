package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorData;
import sharespot.services.devicerecordsbackend.domain.model.sensors.ProcessedSensorDataWithRecord;
import sharespot.services.devicerecordsbackend.domainservices.DeviceRecordCache;

@Service
public class RecordAppenderService {

    private final DeviceRecordCache cache;
    private final ProcessedSensorDataWithRecordMapper dataWithRecordMapper;
    private final ProcessedSensorDataMapper dataMapper;

    public RecordAppenderService(DeviceRecordCache cache,
                                 ProcessedSensorDataWithRecordMapper dataWithRecordMapper,
                                 ProcessedSensorDataMapper dataMapper) {
        this.cache = cache;
        this.dataWithRecordMapper = dataWithRecordMapper;
        this.dataMapper = dataMapper;
    }

    public ProcessedSensorDataWithRecordsDTO tryToAppend(ProcessedSensorDataDTO dto) {
        var processedSensorData = dataMapper.dtoToDomain(dto);
        var processedSensorDataWithRecord = appendRecord(processedSensorData);
        return dataWithRecordMapper.domainToDto(processedSensorDataWithRecord);
    }

    private ProcessedSensorDataWithRecord appendRecord(ProcessedSensorData data) {
        var deviceId = new DeviceId(data.getDevice().getId());
        var records = cache.findByDeviceId(deviceId).orElseGet(() -> DeviceRecords.empty(deviceId));
        return new ProcessedSensorDataWithRecord(data, records);
    }
}
