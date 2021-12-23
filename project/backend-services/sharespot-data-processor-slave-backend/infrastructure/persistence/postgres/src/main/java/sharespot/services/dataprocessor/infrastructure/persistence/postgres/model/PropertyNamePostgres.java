package sharespot.services.dataprocessor.infrastructure.persistence.postgres.model;

import javax.persistence.Embeddable;

@Embeddable
public class PropertyNamePostgres {

    /**
     * Postgres does not support enums, so to address this i've made my "enum"
     * <p>
     * DATA_ID -> 0
     * DEVICE_ID -> 1
     * DEVICE_NAME -> 2
     * DEVICE_RECORDS -> 3
     * REPORTED_AT -> 4
     * LATITUDE -> 5
     * LONGITUDE -> 6
     * TEMPERATURE -> 7
     */
    public int value;

    public static PropertyNamePostgres dataId() {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = 0;
        return propertyName;
    }

    public static PropertyNamePostgres deviceId() {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = 1;
        return propertyName;
    }

    public static PropertyNamePostgres deviceName() {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = 2;
        return propertyName;
    }

    public static PropertyNamePostgres deviceRecords() {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = 3;
        return propertyName;
    }

    public static PropertyNamePostgres reportedAr() {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = 4;
        return propertyName;
    }

    public static PropertyNamePostgres latitude() {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = 5;
        return propertyName;
    }

    public static PropertyNamePostgres longitude() {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = 6;
        return propertyName;
    }

    public static PropertyNamePostgres temperature() {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = 7;
        return propertyName;
    }
}
