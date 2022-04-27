package pt.sensae.services.device.commander.backend.domain.model.records;

public record SensorDataRecordEntry(SensorDataRecordLabel label, String content) implements RecordEntry {
    @Override
    public String getLabel() {
        return label.value();
    }

    @Override
    public String getContent() {
        return content;
    }

    public boolean has(SensorDataRecordLabel label) {
        return this.label.equals(label);
    }
}
