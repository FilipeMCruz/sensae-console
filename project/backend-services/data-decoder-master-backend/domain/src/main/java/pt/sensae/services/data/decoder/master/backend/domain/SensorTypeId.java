package pt.sensae.services.data.decoder.master.backend.domain;

import pt.sensae.services.data.decoder.master.backend.domain.exceptions.InvalidDecoderException;

import java.util.Objects;

public class SensorTypeId {

    private final String value;

    private SensorTypeId(String value) {
        if (!value.matches("[a-zA-Z\\d]+")) {
            throw new InvalidDecoderException("Invalid Id: value must have numbers and letters only");
        }
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
