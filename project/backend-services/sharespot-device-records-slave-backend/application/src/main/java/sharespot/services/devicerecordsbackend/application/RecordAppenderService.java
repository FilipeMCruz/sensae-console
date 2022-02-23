package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.devicerecordsbackend.domain.model.permissions.DevicePermissions;
import sharespot.services.devicerecordsbackend.domain.model.permissions.Permissions;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domainservices.DeviceDomainsCache;
import sharespot.services.devicerecordsbackend.domainservices.DeviceRecordCache;

@Service
public class RecordAppenderService {

    private final DeviceRecordCache cache;
    private final DeviceDomainsCache permissionsCache;
    private final ProcessedSensorDataWithRecordMapper dataWithRecordMapper;

    public RecordAppenderService(DeviceRecordCache cache,
                                 DeviceDomainsCache permissionsCache,
                                 ProcessedSensorDataWithRecordMapper dataWithRecordMapper) {
        this.cache = cache;
        this.permissionsCache = permissionsCache;
        this.dataWithRecordMapper = dataWithRecordMapper;
    }

    public ProcessedSensorDataDTO tryToAppend(ProcessedSensorDataDTO dto) {
        permissionsCache.verify(
                new DevicePermissions(
                        new DeviceId(dto.device.id),
                        new Permissions(dto.device.domains.read, dto.device.domains.readWrite)));
        return cache.findByDeviceId(new DeviceId(dto.device.id))
                .map(deviceRecords -> dataWithRecordMapper.domainToDto(dto, deviceRecords))
                .orElseGet(() -> dataWithRecordMapper.domainToDto(dto));
    }
}
