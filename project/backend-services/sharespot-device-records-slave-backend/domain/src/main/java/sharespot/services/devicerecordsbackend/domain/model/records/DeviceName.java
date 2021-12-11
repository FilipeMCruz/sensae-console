package sharespot.services.devicerecordsbackend.domain.model.records;

public record DeviceName(String value) {
    public static DeviceName empty() {
        return new DeviceName("");
    }
}
