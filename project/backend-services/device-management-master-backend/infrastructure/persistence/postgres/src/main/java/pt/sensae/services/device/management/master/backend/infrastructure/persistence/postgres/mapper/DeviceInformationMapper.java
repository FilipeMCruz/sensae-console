package pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.commands.*;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.management.master.backend.domain.model.records.BasicRecordEntry;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceRecords;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.DeviceStaticData;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.DeviceStaticDataEntry;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.StaticDataLabel;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.DeviceRef;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevice;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DeviceInformationMapper {
    public static DeviceInformationPostgres domainToPostgres(DeviceInformation records) {
        var postgres = new DeviceInformationPostgres();
        postgres.deviceId = records.device().id().value().toString();
        postgres.name = records.device().name().value();
        postgres.downlink = records.device().downlink().value();
        postgres.entries = records.deviceRecords().entries().stream().map(e -> {
            var entry = new DeviceRecordEntryPostgres();
            entry.type = DeviceRecordEntryTypePostgres.basic();
            entry.content = e.content();
            entry.label = e.label();
            entry.records = postgres;
            return entry;
        }).collect(Collectors.toSet());

        postgres.entries.addAll(records.staticData().entries().stream().map(e -> {
            var entry = new DeviceRecordEntryPostgres();
            entry.type = DeviceRecordEntryTypePostgres.sensorData();
            entry.content = e.content();
            entry.label = e.label().value();
            entry.records = postgres;
            return entry;
        }).collect(Collectors.toSet()));

        postgres.subSensors = records.subDevices().entries().stream().map(sub -> {
            var entry = new DeviceSubSensorPostgres();
            entry.subDeviceRef = sub.ref().value();
            entry.subDeviceId = sub.id().value().toString();
            entry.controller = postgres;
            return entry;
        }).collect(Collectors.toSet());

        postgres.commands = records.commands().entries().stream().map(com -> {
            var entry = new DeviceCommandEntryPostgres();
            entry.device = postgres;
            entry.id = com.id().value();
            entry.name = com.name().value();
            entry.port = com.port().value();
            entry.payload = com.payload().value();
            entry.subDeviceRef = com.ref().value();
            return entry;
        }).collect(Collectors.toSet());

        return postgres;
    }

    public static DeviceInformation postgresToDomain(DeviceInformationPostgres information) {
        Set<BasicRecordEntry> records = information.entries.stream()
                .filter(e -> e.type.equals(DeviceRecordEntryTypePostgres.basic()))
                .map(e -> new BasicRecordEntry(e.label, e.content))
                .collect(Collectors.toSet());

        Set<DeviceStaticDataEntry> staticData = information.entries.stream()
                .filter(e -> e.type.equals(DeviceRecordEntryTypePostgres.sensorData()))
                .map(e -> new DeviceStaticDataEntry(StaticDataLabel.give(e.label), e.content))
                .collect(Collectors.toSet());

        var deviceId = new DeviceId(UUID.fromString(information.deviceId));
        var deviceName = new DeviceName(information.name);
        var deviceDownlink = new DeviceDownlink(information.downlink);

        var subDevices = information.subSensors.stream()
                .map(sub -> new SubDevice(new DeviceId(UUID.fromString(sub.subDeviceId)), new DeviceRef(sub.subDeviceRef)))
                .collect(Collectors.toSet());

        var commands = information.commands.stream()
                .map(c -> new CommandEntry(CommandId.of(c.id), CommandName.of(c.name), CommandPayload.of(c.payload), CommandPort.of(c.port), DeviceRef.of(c.subDeviceRef)))
                .collect(Collectors.toSet());

        return new DeviceInformation(new Device(deviceId, deviceName, deviceDownlink), new DeviceRecords(records), new DeviceStaticData(staticData), new SubDevices(subDevices), new DeviceCommands(commands));
    }
}
