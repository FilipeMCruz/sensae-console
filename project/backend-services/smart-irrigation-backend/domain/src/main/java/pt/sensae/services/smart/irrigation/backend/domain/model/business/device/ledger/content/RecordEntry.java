package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content;

public record RecordEntry(String label, String content) {
    public static RecordEntry of(String label, String content) {
        return new RecordEntry(label, content);
    }
}
