package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content;

public record RemoteControl(boolean value, boolean isQueued) {
    public static RemoteControl of(Boolean value) {
        return new RemoteControl(value, false);
    }

    public RemoteControl queue() {
        return new RemoteControl(this.value, true);
    }
}
