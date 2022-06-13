package pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone;

public record IrrigationZoneName(String value) {

    public static IrrigationZoneName of(String name) {
        return new IrrigationZoneName(name);
    }
}
