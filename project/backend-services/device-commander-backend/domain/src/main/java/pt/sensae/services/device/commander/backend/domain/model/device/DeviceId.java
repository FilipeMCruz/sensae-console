package pt.sensae.services.device.commander.backend.domain.model.device;

import java.util.UUID;

public record DeviceId(UUID value) {
    public static DeviceId of(UUID deviceId) {
        return new DeviceId(deviceId);
    }
}
