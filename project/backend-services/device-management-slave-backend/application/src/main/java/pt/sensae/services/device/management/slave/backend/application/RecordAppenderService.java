package pt.sensae.services.device.management.slave.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.DeviceWithSubDevices;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.management.slave.backend.domainservices.DeviceRecordCache;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

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
