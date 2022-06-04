package pt.sensae.services.device.management.master.backend.domain.model.device;

import java.util.UUID;

public record DeviceId(UUID value) {
    public static DeviceId of(UUID uuid) {
        return new DeviceId(uuid);
    }
}
