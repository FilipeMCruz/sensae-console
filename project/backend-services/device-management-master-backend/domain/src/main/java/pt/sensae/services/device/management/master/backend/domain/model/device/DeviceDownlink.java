package pt.sensae.services.device.management.master.backend.domain.model.device;

public record DeviceDownlink(String value) {
    public boolean exists() {
        return value != null && !value.isBlank();
    }
}
