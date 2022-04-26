package pt.sensae.services.device.management.master.backend.domain.model.records;

public record DeviceName(String value) {
    public static DeviceName empty() {
        return new DeviceName("");
    }
}
