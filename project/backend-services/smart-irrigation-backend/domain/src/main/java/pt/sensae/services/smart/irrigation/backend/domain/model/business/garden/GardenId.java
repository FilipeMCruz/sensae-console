package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import java.util.UUID;

public record GardenId(UUID value) {
    public static GardenId of(String id) {
        return new GardenId(UUID.fromString(id));
    }
}
