package pt.sensae.services.device.commander.backend.infrastructure.persistence.postgres.model;

import javax.persistence.Embeddable;

@Embeddable
public class DeviceRecordEntryTypePostgres {

    /**
     * Postgres does not support enums, so to address this i've made my "enum"
     * <p>
     * BASIC -> 0
     * SENSOR_DATA -> 1
     */
    public int type;

    public static DeviceRecordEntryTypePostgres basic() {
        var type = new DeviceRecordEntryTypePostgres();
        type.type = 0;
        return type;
    }

    public static DeviceRecordEntryTypePostgres sensorData() {
        var type = new DeviceRecordEntryTypePostgres();
        type.type = 1;
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceRecordEntryTypePostgres that)) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return type;
    }
}
