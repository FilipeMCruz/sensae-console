package pt.sensae.services.smart.irrigation.backend.domain.model.business.device;

import java.util.UUID;

public record DeviceId(UUID value) {
    public static DeviceId of(UUID id) {
        return new DeviceId(id);
    }
}
