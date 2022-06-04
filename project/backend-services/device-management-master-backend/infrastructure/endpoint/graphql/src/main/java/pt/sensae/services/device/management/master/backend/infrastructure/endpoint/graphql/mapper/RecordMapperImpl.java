package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.application.DeviceDTO;
import pt.sensae.services.device.management.master.backend.application.DeviceInformationDTO;
import pt.sensae.services.device.management.master.backend.application.RecordMapper;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.commands.*;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.management.master.backend.domain.model.exceptions.NotValidException;
import pt.sensae.services.device.management.master.backend.domain.model.records.BasicRecordEntry;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceRecords;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.DeviceStaticData;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.DeviceStaticDataEntry;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.StaticDataLabel;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.DeviceRef;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevice;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;
import pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model.*;

import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecordMapperImpl implements RecordMapper {

    @Override
    public DeviceInformation dtoToDomain(DeviceInformationDTO dto) {
        var deviceDTO = (DeviceInformationDTOImpl) dto;

        Set<BasicRecordEntry> records = deviceDTO.records.stream()
                .map(e -> new BasicRecordEntry(e.label, e.content))
                .collect(Collectors.toSet());

        Set<DeviceStaticDataEntry> staticData = deviceDTO.staticData.stream()
                .map(e -> new DeviceStaticDataEntry(dtoToModel(e.label), e.content))
                .collect(Collectors.toSet());

        var subDevices = deviceDTO.subDevices.stream()
                .map(sub -> new SubDevice(new DeviceId(UUID.fromString(sub.id)), new DeviceRef(sub.ref)))
                .collect(Collectors.toSet());

        var hasSensorLabelDuplicates = staticData.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .anyMatch(occurrences -> occurrences != 1);

        var hasDuplicates = records.stream()
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

        return new DeviceInformation(new Device(id, name, downlink), new DeviceRecords(records), new DeviceStaticData(staticData), new SubDevices(subDevices), new DeviceCommands(commands));
    }

    @Override
    public DeviceInformationDTO domainToDto(DeviceInformation domain) {
        var dto = new DeviceInformationDTOImpl();
        var deviceDTO = new DeviceDTOImpl();
        deviceDTO.id = domain.device().id().value().toString();
        deviceDTO.name = domain.device().name().value();
        deviceDTO.downlink = domain.device().downlink().value();
        dto.device = deviceDTO;
        dto.records = domain.deviceRecords().entries().stream().map(e -> {
            var entry = new RecordEntryDTOImpl();
            entry.content = e.content();
            entry.label = e.label();
            return entry;
        }).collect(Collectors.toSet());

        dto.staticData = domain.staticData().entries().stream().map(e -> {
            var entry = new DeviceStaticDataDTOImpl();
            entry.content = e.content();
            entry.label = modelToDto(e.label());
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

    private DeviceStaticDataLabelDTO modelToDto(StaticDataLabel model) {
        return switch (model) {
            case GPS_LATITUDE -> DeviceStaticDataLabelDTO.GPS_LATITUDE;
            case GPS_LONGITUDE -> DeviceStaticDataLabelDTO.GPS_LONGITUDE;
        };
    }

    private StaticDataLabel dtoToModel(DeviceStaticDataLabelDTO model) {
        return switch (model) {
            case GPS_LATITUDE -> StaticDataLabel.GPS_LATITUDE;
            case GPS_LONGITUDE -> StaticDataLabel.GPS_LONGITUDE;
        };
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
