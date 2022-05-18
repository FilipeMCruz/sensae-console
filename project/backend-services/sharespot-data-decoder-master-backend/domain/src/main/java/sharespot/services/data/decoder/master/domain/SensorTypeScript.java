package sharespot.services.data.decoder.master.domain;

public record SensorTypeScript(String value) {

    public static SensorTypeScript of(String script) {
        return new SensorTypeScript(script);
    }
}
