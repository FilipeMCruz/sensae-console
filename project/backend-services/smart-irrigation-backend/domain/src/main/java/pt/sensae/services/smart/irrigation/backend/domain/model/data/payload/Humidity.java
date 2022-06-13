package pt.sensae.services.smart.irrigation.backend.domain.model.data.payload;

public record Humidity(Float relativePercentage) {
    public static Humidity of(Float relativePercentage) {
        return new Humidity(relativePercentage);
    }
}
