package pt.sensae.services.smart.irrigation.backend.domain.model.data.payload;

public record Illuminance(Float lux) {
    public static Illuminance of(Float lux) {
        return new Illuminance(lux);
    }
}
