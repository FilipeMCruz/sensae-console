package pt.sensae.services.device.commander.domain.subDevices;

public record DeviceRef(Integer value) {
    public static DeviceRef of(Integer value) {
        return new DeviceRef(value);
    }

    public boolean isSelf() {
        return value == 0;
    }
}