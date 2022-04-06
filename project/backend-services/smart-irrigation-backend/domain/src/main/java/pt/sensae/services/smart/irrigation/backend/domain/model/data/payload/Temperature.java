package pt.sensae.services.smart.irrigation.backend.domain.model.data.payload;

public record Temperature(Float celsius) {
    public static Temperature of(Float celsius) {
        return new Temperature(celsius);
    }
}
