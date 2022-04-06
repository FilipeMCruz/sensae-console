package pt.sensae.services.smart.irrigation.backend.domain.model.data.payload;

public record Humidity(Float gramsPerCubicMeter) {
    public static Humidity of(Float gramsPerCubicMeter) {
        return new Humidity(gramsPerCubicMeter);
    }
}
