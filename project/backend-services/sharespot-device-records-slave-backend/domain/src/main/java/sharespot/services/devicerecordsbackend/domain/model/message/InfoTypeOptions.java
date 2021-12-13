package sharespot.services.devicerecordsbackend.domain.model.message;

public enum InfoTypeOptions {
    ENCODED,
    DECODED,
    PROCESSED;

    public String value() {
        return this.name().toLowerCase();
    }
}
