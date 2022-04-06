package pt.sensae.services.smart.irrigation.backend.domain.model.data.payload;

public record StovePayload(Temperature illuminance, Humidity soilMoisture) implements Payload {
}
