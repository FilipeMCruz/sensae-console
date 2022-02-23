package sharespot.services.devicerecordsbackend.domain.model.records;

import sharespot.services.devicerecordsbackend.domain.model.DeviceId;

public record DeviceRecords(Device device,
                            Records records) {
    public static DeviceRecords empty(DeviceId id) {
        var records = Records.empty();
        var name = DeviceName.empty();
        return new DeviceRecords(new Device(id,name), records);
    }
}
