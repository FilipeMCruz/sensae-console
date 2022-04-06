package pt.sensae.services.smart.irrigation.backend.domain.model.data.payload;

public record ParkPayload(Illuminance illuminance, SoilMoisture soilMoisture) implements Payload {
}
