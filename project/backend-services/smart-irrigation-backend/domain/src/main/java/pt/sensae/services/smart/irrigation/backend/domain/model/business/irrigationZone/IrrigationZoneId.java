package pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone;

import java.util.UUID;

public record IrrigationZoneId(UUID value) {
    public static IrrigationZoneId of(UUID id) {
        return new IrrigationZoneId(id);
    }

    public static IrrigationZoneId create() {
        return new IrrigationZoneId(UUID.randomUUID());
    }
}
