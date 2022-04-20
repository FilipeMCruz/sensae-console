package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.devicerecordsbackend.application.DeviceDTO;
import sharespot.services.devicerecordsbackend.application.DeviceRecordDTO;
import sharespot.services.devicerecordsbackend.application.RecordMapper;
import sharespot.services.devicerecordsbackend.domain.model.records.Device;
import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.exceptions.NotValidException;
import sharespot.services.devicerecordsbackend.domain.model.records.*;
import sharespot.services.devicerecordsbackend.domain.model.subDevices.DeviceRef;
import sharespot.services.devicerecordsbackend.domain.model.subDevices.SubDevice;
import sharespot.services.devicerecordsbackend.domain.model.subDevices.SubDevices;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model.DeviceDTOImpl;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model.DeviceRecordDTOImpl;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model.RecordEntryDTOImpl;
import sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.model.RecordTypeDTOImpl;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecordMapperImpl implements RecordMapper {

    @Override
    public DeviceRecords dtoToDomain(DeviceRecordDTO dto) {
        var deviceDTO = (DeviceRecordDTOImpl) dto;

        List<RecordEntry> records = deviceDTO.entries.stream().map(e -> {
            if (RecordTypeDTOImpl.BASIC.equals(e.type)) {
                return new BasicRecordEntry(e.label, e.content);
            } else {
                var label = SensorDataRecordLabel.give(e.label);
                return new SensorDataRecordEntry(label, e.content);
            }
        }).collect(Collectors.toList());

        var subDevices = deviceDTO.subDevices.stream()
                .map(sub -> new SubDevice(new DeviceId(UUID.fromString(sub.id)), new DeviceRef(sub.ref)))
                .collect(Collectors.toSet());

        var hasSensorLabelDuplicates = records.stream()
                .filter(e -> e instanceof SensorDataRecordEntry)
                .map(e -> ((SensorDataRecordEntry) e).label())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .anyMatch(occurrences -> occurrences != 1);

        var hasDuplicates = records.stream()
                .filter(e -> e instanceof BasicRecordEntry)
                .map(e -> ((BasicRecordEntry) e).label())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .anyMatch(occurrences -> occurrences != 1);

        var hasSubDevicesRefDuplicate = subDevices.stream()
                .map(SubDevice::ref)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .anyMatch(occurrences -> occurrences != 1);

        var hasSubDevicesIdDuplicate = subDevices.stream()
                .map(SubDevice::id)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .anyMatch(occurrences -> occurrences != 1);

        if (hasSubDevicesRefDuplicate) {
            throw new NotValidException("A device can't have two equal refs to sub devices");
        }

        if (hasSubDevicesIdDuplicate) {
            throw new NotValidException("A device can't have the same sub device for two or more refs");
        }

        if (hasSensorLabelDuplicates) {
            throw new NotValidException("A record can't have two equal Sensor Data Labels");
        }

        if (hasDuplicates) {
            throw new NotValidException("A record can't have two equal Basic Labels");
        }

        var id = new DeviceId(UUID.fromString(deviceDTO.device.id));
        var name = new DeviceName(deviceDTO.device.name);

        return new DeviceRecords(new Device(id, name), new Records(records), new SubDevices(subDevices));
    }

    @Override
    public DeviceRecordDTO domainToDto(DeviceRecords domain) {
        var dto = new DeviceRecordDTOImpl();
        var deviceDTO = new DeviceDTOImpl();
        deviceDTO.id = domain.device().id().value().toString();
        deviceDTO.name = domain.device().name().value();
        dto.device = deviceDTO;
        dto.entries = domain.records().entries().stream().map(e -> {
            var entry = new RecordEntryDTOImpl();
            if (e instanceof BasicRecordEntry) {
                entry.type = RecordTypeDTOImpl.BASIC;
            } else {
                entry.type = RecordTypeDTOImpl.SENSOR_DATA;
            }
            entry.content = e.getContent();
            entry.label = e.getLabel();
            return entry;
        }).collect(Collectors.toSet());
        return dto;
    }

    @Override
    public DeviceId dtoToDomain(DeviceDTO dto) {
        var deviceDTO = (DeviceDTOImpl) dto;
        return new DeviceId(UUID.fromString(deviceDTO.id));
    }

    @Override
    public DeviceDTO domainToDto(DeviceId domain) {
        var deviceDTO = new DeviceDTOImpl();
        deviceDTO.id = domain.value().toString();
        return deviceDTO;
    }
}
