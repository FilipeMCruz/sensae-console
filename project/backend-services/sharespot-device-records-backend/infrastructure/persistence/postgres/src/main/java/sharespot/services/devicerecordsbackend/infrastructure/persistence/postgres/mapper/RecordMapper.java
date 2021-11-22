package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.devicerecordsbackend.domain.model.records.*;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.DeviceRecordEntryPostgres;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.DeviceRecordEntryTypePostgres;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.DeviceRecordsPostgres;

import java.util.List;
import java.util.stream.Collectors;

public class RecordMapper {

    public static DeviceRecordsPostgres domainToPostgres(DeviceRecords records) {
        var postgres = new DeviceRecordsPostgres();
        postgres.deviceId = records.getDeviceId().value();
        postgres.entries = records.getRecords().entries().stream().map(e -> {
            var entry = new DeviceRecordEntryPostgres();
            if (e instanceof BasicRecordEntry) {
                entry.type = DeviceRecordEntryTypePostgres.basic();
            } else {
                entry.type = DeviceRecordEntryTypePostgres.sensorData();
            }
            entry.content = e.getContent();
            entry.label = e.getLabel();
            return entry;
        }).collect(Collectors.toSet());
        return postgres;
    }

    public static DeviceRecords postgresToDomain(DeviceRecordsPostgres records) {
        List<RecordEntry> collect = records.entries.stream().map(e -> {
            if (e.type.equals(DeviceRecordEntryTypePostgres.basic())) {
                var label = SensorDataRecordLabel.give(e.label);
                return new SensorDataRecordEntry(label, e.content);
            } else {
                return new BasicRecordEntry(e.label, e.content);
            }
        }).collect(Collectors.toList());

        return new DeviceRecords(new DeviceId(records.deviceId), new Records(collect));
    }
}
