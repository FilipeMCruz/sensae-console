package sharespot.services.locationtrackingbackend.domain.message;

public enum InfoTypeOptions {
    ENCODED,
    DECODED,
    PROCESSED;

    public String value() {
        return this.name().toLowerCase();
    }
}
