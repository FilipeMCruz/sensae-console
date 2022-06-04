package pt.sensae.services.device.management.slave.backend.domain.model.staticData;

public record DeviceStaticDataEntry(StaticDataLabel label, String content) {
    public boolean has(StaticDataLabel other) {
        return this.label.equals(other);
    }
}
