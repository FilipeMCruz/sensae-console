package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import java.util.UUID;

public record GardeningAreaId(UUID value) {
    public static GardeningAreaId of(UUID id) {
        return new GardeningAreaId(id);
    }

    public static GardeningAreaId create() {
        return new GardeningAreaId(UUID.randomUUID());
    }
}
