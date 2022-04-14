package pt.sensae.services.smart.irrigation.backend.domain.model.data.payload;

public record SoilMoisture(Float percentage) {
    public static SoilMoisture of(Float percentage) {
        return new SoilMoisture(percentage);
    }
}
