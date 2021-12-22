package sharespot.services.lgt92gpsdataprocessor.application;

public class SensorTypeId {

    private final String value;

    private SensorTypeId(String value) {
        this.value = value;
    }

    public boolean same(SensorTypeId id) {
        return id.value.equals(value);
    }

    public static SensorTypeId of(String value) {
        return new SensorTypeId(value);
    }
}
