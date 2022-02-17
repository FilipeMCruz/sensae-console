package sharespot.services.identitymanagementbackend.domain.model.records;

public record BasicRecordEntry(String label, String content) implements RecordEntry {
    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getContent() {
        return content;
    }
}
