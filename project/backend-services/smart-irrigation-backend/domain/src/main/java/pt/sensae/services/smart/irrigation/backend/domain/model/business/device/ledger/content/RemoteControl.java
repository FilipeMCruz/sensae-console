package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content;

import java.util.Objects;

public record RemoteControl(boolean value, boolean isQueued) {

    public static RemoteControl of(Boolean value) {
        return new RemoteControl(value, false);
    }

    public RemoteControl queue() {
        return new RemoteControl(this.value, true);
    }

    public RemoteControl reset() {
        return new RemoteControl(this.value, false);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RemoteControl) obj;
        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "RemoteControl[" + "value=" + value + ", " + "isQueued=" + isQueued + ']';
    }

}
