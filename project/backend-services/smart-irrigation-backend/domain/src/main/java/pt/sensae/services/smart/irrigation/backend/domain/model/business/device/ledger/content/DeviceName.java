package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content;

public record DeviceName(String value) {

    public static DeviceName of(String name) {
        return new DeviceName(name);
    }
}
