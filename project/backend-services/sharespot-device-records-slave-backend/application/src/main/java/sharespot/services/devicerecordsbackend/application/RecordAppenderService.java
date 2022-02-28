package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domainservices.DeviceRecordCache;

@Service
public class RecordAppenderService {

    private final DeviceRecordCache cache;
    private final ProcessedSensorDataWithRecordMapper dataWithRecordMapper;

    public RecordAppenderService(DeviceRecordCache cache,
                                 ProcessedSensorDataWithRecordMapper dataWithRecordMapper) {
        this.cache = cache;
        this.dataWithRecordMapper = dataWithRecordMapper;
    }

    public ProcessedSensorDataDTO tryToAppend(ProcessedSensorDataDTO dto) {
        return cache.findByDeviceId(new DeviceId(dto.device.id))
                .map(deviceRecords -> dataWithRecordMapper.domainToDto(dto, deviceRecords))
                .orElseGet(() -> dataWithRecordMapper.domainToDto(dto));
    }
}
