package sharespot.services.devicerecordsbackend.domain.model.message;

public enum RecordsOptions {
    WITH_RECORDS,
    WITHOUT_RECORDS;

    public String value() {
        return this.name().toLowerCase();
    }
}
