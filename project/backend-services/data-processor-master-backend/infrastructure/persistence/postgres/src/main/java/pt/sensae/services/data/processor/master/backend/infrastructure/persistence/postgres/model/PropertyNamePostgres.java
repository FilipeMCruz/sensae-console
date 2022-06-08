package pt.sensae.services.data.processor.master.backend.infrastructure.persistence.postgres.model;

import javax.persistence.Embeddable;

@Embeddable
public class PropertyNamePostgres {

    /**
     * Postgres does not support enums, so to address this i've made my "enum"
     * <p>
     * DATA_ID -> 0
     * DEVICE_ID -> 1
     * DEVICE_NAME -> 2
     * REPORTED_AT -> 4
     * LATITUDE -> 5
     * LONGITUDE -> 6
     * TEMPERATURE -> 7
     * MOTION -> 8
     * VELOCITY -> 9
     * AQI -> 10
     * HUMIDITY -> 11
     * PRESSURE -> 12
     * SOIL_MOISTURE -> 13
     * ILLUMINANCE -> 14
     * ALTITUDE -> 15
     * BATTERY_PERCENTAGE -> 16
     * BATTERY_VOLTS -> 17
     */
    public int value;

    public static PropertyNamePostgres of(int value) {
        var propertyName = new PropertyNamePostgres();
        propertyName.value = value;
        return propertyName;
    }
}
