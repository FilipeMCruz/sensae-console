package pt.sensae.services.device.management.flow.domain.device;

public record DeviceDownlink(String value) {
    public boolean isValid() {
        return !this.value.isBlank();
    }
}
