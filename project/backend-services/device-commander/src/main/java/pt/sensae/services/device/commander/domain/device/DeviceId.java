package pt.sensae.services.device.commander.domain.device;

import java.util.UUID;

public record DeviceId(UUID value) {
    public static DeviceId of(UUID deviceId) {
        return new DeviceId(deviceId);
    }
}
