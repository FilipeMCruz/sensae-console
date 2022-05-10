package pt.sensae.services.device.management.slave.backend.domain.model.device;

public record DeviceDownlink(String value) {
    public boolean isValid() {
        return !this.value.isBlank();
    }
}
