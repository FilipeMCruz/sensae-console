package pt.sensae.services.data.decoder.master.backend.domain;

public record SensorTypeScript(String value) {

    public static SensorTypeScript of(String script) {
        return new SensorTypeScript(script);
    }
}
