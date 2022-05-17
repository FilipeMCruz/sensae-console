package sharespot.services.data.decoder.domain;

public class DataDecoder {

    private final SensorTypeId id;

    private final SensorTypeScript script;

    public DataDecoder(SensorTypeId id, SensorTypeScript script) {
        this.id = id;
        this.script = script;
    }

    public SensorTypeScript getScript() {
        return script;
    }

    public SensorTypeId getId() {
        return id;
    }
}
