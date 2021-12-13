package sharespot.services.fastdatastore.application.message;

public enum TempCDataOptions {
    WITH_TEMPC_DATA,
    WITHOUT_TEMPC_DATA;

    public String value() {
        return this.name().toLowerCase();
    }
}
