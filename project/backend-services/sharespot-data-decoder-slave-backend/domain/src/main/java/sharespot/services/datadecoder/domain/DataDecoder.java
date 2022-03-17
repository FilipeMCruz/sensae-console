package sharespot.services.datadecoder.domain;

public class DataDecoder {

    private final SensorTypeId id;

    private final String script;

    public DataDecoder(SensorTypeId id, String transform) {
        this.id = id;
        this.script = transform;
    }

    public String getScript() {
        return script;
    }
}
