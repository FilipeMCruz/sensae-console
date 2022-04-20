package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.domain.model.DeviceWithSubDevices;
import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.device.DeviceName;
import sharespot.services.devicerecordsbackend.domainservices.DeviceRecordCache;

import java.util.List;

@Service
public class RecordAppenderService {

    private final DeviceRecordCache cache;
    private final ProcessedSensorDataWithRecordMapper dataWithRecordMapper;

    public RecordAppenderService(DeviceRecordCache cache,
                                 ProcessedSensorDataWithRecordMapper dataWithRecordMapper) {
        this.cache = cache;
        this.dataWithRecordMapper = dataWithRecordMapper;
    }

    public DeviceWithSubDevices tryToAppend(ProcessedSensorDataDTO dto) {
        var byDeviceId = cache.findByDeviceId(new DeviceId(dto.device.id), new DeviceName(dto.device.name));
        return dataWithRecordMapper.domainToDto(dto, byDeviceId);
    }
}
