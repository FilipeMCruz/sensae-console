package sharespot.services.datastore.application.model;

public enum RecordsOptions {
    WITH_RECORDS,
    WITHOUT_RECORDS;

    public String value() {
        return this.name().toLowerCase();
    }
}
