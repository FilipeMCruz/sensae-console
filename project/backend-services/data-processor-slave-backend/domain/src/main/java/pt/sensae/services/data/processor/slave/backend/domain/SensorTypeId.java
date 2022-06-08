package pt.sensae.services.data.processor.slave.backend.domain;

import java.util.Objects;

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

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorTypeId that)) return false;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
