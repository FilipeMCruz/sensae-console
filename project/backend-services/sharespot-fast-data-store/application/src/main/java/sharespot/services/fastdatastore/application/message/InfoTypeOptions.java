package sharespot.services.fastdatastore.application.message;

public enum InfoTypeOptions {
    ENCODED,
    DECODED,
    PROCESSED;

    public String value() {
        return this.name().toLowerCase();
    }
}
