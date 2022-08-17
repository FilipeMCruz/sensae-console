package pt.sensae.services.device.ownership.flow.domain;

import java.util.UUID;

public record DeviceId(UUID value) {
    public static DeviceId of(UUID value) {
        return new DeviceId(value);
    }

    public static DeviceId root() {
        return new DeviceId(UUID.fromString("8c78880e-9e52-4ab4-8f87-d88bbe3bf116"));
    }
}
