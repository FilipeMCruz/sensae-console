package sharespot.services.identitymanagementbackend.domain.identity.device;

import java.util.UUID;

public record DeviceId(UUID value) {
    public static DeviceId of(UUID value) {
        return new DeviceId(value);
    }
}
