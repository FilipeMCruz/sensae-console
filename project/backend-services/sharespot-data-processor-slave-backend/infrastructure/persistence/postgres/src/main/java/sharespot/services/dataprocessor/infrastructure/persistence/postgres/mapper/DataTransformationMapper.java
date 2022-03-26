package sharespot.services.dataprocessor.infrastructure.persistence.postgres.mapper;

import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformation;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformations;
import sharespot.services.dataprocessor.domain.DataTransformation;
import sharespot.services.dataprocessor.domain.SensorTypeId;
import sharespot.services.dataprocessor.infrastructure.persistence.postgres.model.DataTransformationPostgres;
import sharespot.services.dataprocessor.infrastructure.persistence.postgres.model.PropertyNamePostgres;

public class DataTransformationMapper {

    public static DataTransformation postgresToDomain(DataTransformationPostgres postgres) {
        var device = SensorTypeId.of(postgres.deviceType);
        var properties = postgres.entries
                .stream()
                .map(p -> PropertyTransformation.create(p.oldPath, postgresToDomain(p.name)))
                .toArray(PropertyTransformation[]::new);
        var transformations = PropertyTransformations.of(properties);
        return new DataTransformation(device, transformations);
    }

    private static PropertyName postgresToDomain(PropertyNamePostgres postgres) {
        return switch (postgres.value) {
            case 0 -> PropertyName.DATA_ID;
            case 1 -> PropertyName.DEVICE_ID;
            case 2 -> PropertyName.DEVICE_NAME;
            case 4 -> PropertyName.REPORTED_AT;
            case 5 -> PropertyName.LATITUDE;
            case 6 -> PropertyName.LONGITUDE;
            case 7 -> PropertyName.TEMPERATURE;
            case 8 -> PropertyName.MOTION;
            case 9 -> PropertyName.VELOCITY;
            case 10 -> PropertyName.AQI;
            case 11 -> PropertyName.HUMIDITY;
            case 12 -> PropertyName.PRESSURE;
            case 13 -> PropertyName.SOIL_MOISTURE;
            case 14 -> PropertyName.ILLUMINANCE;
            case 15 -> PropertyName.ALTITUDE;
            case 16 -> PropertyName.BATTERY_PERCENTAGE;
            case 17 -> PropertyName.BATTERY_VOLTS;
            default -> throw new IllegalStateException("Unexpected value: " + postgres.value);
        };
    }
}
