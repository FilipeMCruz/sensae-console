package pt.sensae.services.device.management.slave.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.DeviceWithSubDevices;
import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.management.slave.backend.domainservices.DeviceInformationCache;
import pt.sensae.services.device.management.slave.backend.domainservices.UnhandledSensorDataCache;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;

import java.util.Optional;

@Service
public class RecordAppenderService {

    private final DeviceInformationCache cache;
    private final ProcessedSensorDataWithRecordMapper dataWithRecordMapper;

    public RecordAppenderService(DeviceInformationCache cache,
                                 ProcessedSensorDataWithRecordMapper dataWithRecordMapper) {
        this.cache = cache;
        this.dataWithRecordMapper = dataWithRecordMapper;
    }

    public Optional<DeviceWithSubDevices> tryToAppend(SensorDataDTO dto) {
        return cache.findById(new DeviceId(dto.device.id))
                .map(deviceInformation -> dataWithRecordMapper.domainToDto(dto, deviceInformation));
    }
}
