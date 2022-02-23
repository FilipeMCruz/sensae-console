package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.mapper;

import sharespot.services.devicerecordsbackend.domain.model.Device;
import sharespot.services.devicerecordsbackend.domain.model.records.*;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.records.DeviceRecordEntryTypePostgres;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.records.DeviceRecordsPostgres;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecordMapper {

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

        return new DeviceRecords(new Device(deviceId, deviceName), new Records(collect));
    }
}
