package pt.sensae.services.device.commander.domain.staticData;

public record DeviceStaticDataEntry(StaticDataLabel label, String content) {
    public boolean has(StaticDataLabel other) {
        return this.label.equals(other);
    }
}
