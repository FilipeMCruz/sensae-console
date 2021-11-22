package sharespot.services.devicerecordsbackend.domain.model.records;

public class DeviceRecords {

    private final DeviceId deviceId;
    private final Records records;

    public DeviceRecords(DeviceId deviceId, Records records) {
        this.deviceId = deviceId;
        this.records = records;
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }

    public Records getRecords() {
        return records;
    }
}
