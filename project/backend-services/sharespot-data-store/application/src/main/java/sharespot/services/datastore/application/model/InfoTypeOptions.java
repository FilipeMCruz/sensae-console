package sharespot.services.datastore.application.model;

public enum InfoTypeOptions {
    ENCODED,
    DECODED,
    PROCESSED;

    public String value() {
        return this.name().toLowerCase();
    }
}
