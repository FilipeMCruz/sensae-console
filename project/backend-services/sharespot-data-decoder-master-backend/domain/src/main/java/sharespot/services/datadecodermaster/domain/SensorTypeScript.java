package sharespot.services.datadecodermaster.domain;

public class SensorTypeScript {

    private final String value;

    public SensorTypeScript(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SensorTypeScript of(String script) {
        return new SensorTypeScript(script);
    }
}