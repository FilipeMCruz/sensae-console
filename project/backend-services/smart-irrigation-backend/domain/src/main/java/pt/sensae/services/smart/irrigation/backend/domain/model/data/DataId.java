package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import java.util.UUID;

public record DataId(UUID value) {

    public static DataId of(UUID value) {
        return new DataId(value);
    }
}
