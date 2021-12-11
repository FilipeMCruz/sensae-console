package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.DeviceRecordErasedEventDTO;
import sharespot.services.devicerecordsbackend.application.DeviceRecordIndexedEventDTO;
import sharespot.services.devicerecordsbackend.application.RecordEventMapper;
import sharespot.services.devicerecordsbackend.domain.model.records.*;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model.DeviceRecordsDeleteEventDTOImpl;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model.DeviceRecordsIndexEventDTOImpl;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.internal.model.RecordTypeDTOImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecordEventMapperImpl implements RecordEventMapper {

    @Override
    public DeviceRecords dtoToDomain(DeviceRecordIndexedEventDTO dto) {
        var eventDTO = (DeviceRecordsIndexEventDTOImpl) dto;
        var deviceDTO = eventDTO.records;

        List<RecordEntry> records = deviceDTO.entries.stream().map(e -> {
            if (RecordTypeDTOImpl.BASIC.equals(e.type)) {
                return new BasicRecordEntry(e.label, e.content);
            } else {
                var label = SensorDataRecordLabel.give(e.label);
                return new SensorDataRecordEntry(label, e.content);
            }
        }).collect(Collectors.toList());

        var id = new DeviceId(UUID.fromString(deviceDTO.device.id));
        var name = new DeviceName(deviceDTO.device.name);

        return new DeviceRecords(new Device(id, name), new Records(records));
    }

    @Override
    public DeviceId dtoToDomain(DeviceRecordErasedEventDTO dto) {
        var eventDTO = (DeviceRecordsDeleteEventDTOImpl) dto;
        var deviceDTO = eventDTO.records;
        return new DeviceId(deviceDTO.deviceId);
    }
}
