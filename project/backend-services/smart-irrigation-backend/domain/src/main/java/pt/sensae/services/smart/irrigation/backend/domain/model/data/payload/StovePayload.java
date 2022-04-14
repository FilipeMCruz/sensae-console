package pt.sensae.services.smart.irrigation.backend.domain.model.data.payload;

public record StovePayload(Temperature temperature, Humidity humidity) implements Payload {
}
