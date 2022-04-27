package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content;

public record RemoteControl(boolean value) {
    public static RemoteControl of(Boolean value) {
        return new RemoteControl(value);
    }
}
