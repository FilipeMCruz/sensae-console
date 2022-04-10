package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import java.util.UUID;

public record GardeningAreaId(UUID value) {
    public static GardeningAreaId of(String id) {
        return new GardeningAreaId(UUID.fromString(id));
    }
}
