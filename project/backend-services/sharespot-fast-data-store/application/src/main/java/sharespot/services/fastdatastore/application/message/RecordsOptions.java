package sharespot.services.fastdatastore.application.message;

public enum RecordsOptions {
    WITH_RECORDS,
    WITHOUT_RECORDS;

    public String value() {
        return this.name().toLowerCase();
    }
}
