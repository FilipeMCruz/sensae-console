package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceDTO;
import pt.sensae.services.device.management.master.backend.application.DeviceRecordDTO;
import pt.sensae.services.device.management.master.backend.application.RecordMapper;
import pt.sensae.services.device.management.master.backend.domain.model.commands.*;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.management.master.backend.domain.model.exceptions.NotValidException;
import pt.sensae.services.device.management.master.backend.domain.model.records.*;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.DeviceRef;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevice;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model.*;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecordMapperImpl implements RecordMapper {

    @Override
    public DeviceInformation dtoToDomain(DeviceRecordDTO dto) {
        var deviceDTO = (DeviceRecordDTOImpl) dto;

        List<RecordEntry> records = deviceDTO.entries.stream()
                .map(e -> RecordTypeDTOImpl.BASIC.equals(e.type) ?
                        new BasicRecordEntry(e.label, e.content) :
                        new SensorDataRecordEntry(SensorDataRecordLabel.give(e.label), e.content))
                .collect(Collectors.toList());

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
        var downlink = new DeviceDownlink(deviceDTO.device.downlink);

        var commands = deviceDTO.commands.stream()
                .map(c -> new CommandEntry(CommandId.of(c.id), CommandName.of(c.name), CommandPayload.of(c.payload), CommandPort.of(c.port), DeviceRef.of(c.ref)))
                .collect(Collectors.toSet());

        return new DeviceInformation(new Device(id, name, downlink), new Records(records), new SubDevices(subDevices), new DeviceCommands(commands));
    }

    @Override
    public DeviceRecordDTO domainToDto(DeviceInformation domain) {
        var dto = new DeviceRecordDTOImpl();
        var deviceDTO = new DeviceDTOImpl();
        deviceDTO.id = domain.device().id().value().toString();
        deviceDTO.name = domain.device().name().value();
        deviceDTO.downlink = domain.device().downlink().value();
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

        dto.subDevices = domain.subDevices().entries().stream().map(sub -> {
            var subDevice = new SubDeviceDTOImpl();
            subDevice.id = sub.id().value().toString();
            subDevice.ref = sub.ref().value();
            return subDevice;
        }).collect(Collectors.toSet());

        dto.commands = domain.commands().entries().stream().map(e -> {
            var entry = new DeviceCommandDTOImpl();
            entry.id = e.id().value();
            entry.name = e.name().value();
            entry.port = e.port().value();
            entry.payload = e.payload().value();
            entry.ref = e.ref().value();
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
