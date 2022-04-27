package pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.records.*;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.DeviceRef;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevice;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model.DeviceRecordsPostgres;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model.DeviceSubSensorPostgres;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model.DeviceRecordEntryPostgres;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model.DeviceRecordEntryTypePostgres;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecordMapper {
    public static DeviceRecordsPostgres domainToPostgres(DeviceRecords records) {
        var postgres = new DeviceRecordsPostgres();
        postgres.deviceId = records.device().id().value().toString();
        postgres.name = records.device().name().value();
        postgres.entries = records.records().entries().stream().map(e -> {
            var entry = new DeviceRecordEntryPostgres();
            if (e instanceof BasicRecordEntry) {
                entry.type = DeviceRecordEntryTypePostgres.basic();
            } else {
                entry.type = DeviceRecordEntryTypePostgres.sensorData();
            }
            entry.content = e.getContent();
            entry.label = e.getLabel();
            entry.records = postgres;
            return entry;
        }).collect(Collectors.toSet());

        postgres.subSensors = records.subDevices().entries().stream().map(sub -> {
            var entry = new DeviceSubSensorPostgres();
            entry.subDeviceRef = sub.ref().value();
            entry.subDeviceId = sub.id().value().toString();
            entry.controller = postgres;
            return entry;
        }).collect(Collectors.toSet());

        return postgres;
    }

    public static DeviceRecords postgresToDomain(DeviceRecordsPostgres records) {
        List<RecordEntry> collect = records.entries.stream().map(e -> {
            if (e.type.equals(DeviceRecordEntryTypePostgres.sensorData())) {
                var label = SensorDataRecordLabel.give(e.label);
                return new SensorDataRecordEntry(label, e.content);
            } else {
                return new BasicRecordEntry(e.label, e.content);
            }
        }).collect(Collectors.toList());

        var deviceId = new DeviceId(UUID.fromString(records.deviceId));
        var deviceName = new DeviceName(records.name);

        var subDevices = records.subSensors.stream()
                .map(sub -> new SubDevice(new DeviceId(UUID.fromString(sub.subDeviceId)), new DeviceRef(sub.subDeviceRef)))
                .collect(Collectors.toSet());

        return new DeviceRecords(new Device(deviceId, deviceName), new Records(collect), new SubDevices(subDevices));
    }
}