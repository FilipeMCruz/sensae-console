package pt.sensae.services.device.commander.domain.device;

public record DeviceDownlink(String value) {
    public boolean isValid() {
        return !this.value.isBlank();
    }
}
