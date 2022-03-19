package sharespot.services.datadecodermaster.domain;

public class DataDecoder {

    private final SensorTypeId id;

    private final SensorTypeScript script;

    public DataDecoder(SensorTypeId id, SensorTypeScript script) {
        this.id = id;
        this.script = script;
    }

    public SensorTypeId getId() {
        return id;
    }

    public SensorTypeScript getScript() {
        return script;
    }
}
